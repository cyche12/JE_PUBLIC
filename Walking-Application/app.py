import os, traceback
from flask import Flask, request, jsonify, send_from_directory
from flask_cors import CORS
from config import DEBUG, FLASK_SECRET_KEY
from Services.MultiViewAnalyzer import MultiViewAnalyzer
from Services.LabelParser import LabelParser
from Services.PathPlanner import PathPlanner

app=Flask(__name__, static_folder='Static', static_url_path='')
app.config['SECRET_KEY']=FLASK_SECRET_KEY
CORS(app, resources={r"/*": {"origins": "*"}}, methods=["GET","POST","OPTIONS"], allow_headers=["Content-Type"])

@app.route('/')
def index():
    return send_from_directory(app.static_folder,'index.html')

@app.route('/analyze', methods=['POST','OPTIONS'])
def analyze():
    if request.method=='OPTIONS': return ('',200)
    try:
        payload=request.get_json(force=True) or {}
        addr=payload.get('address','').strip()
        if not addr: return jsonify({'error':'No address provided'}),400
        slug=addr.replace(' ','-')
        dist=MultiViewAnalyzer(slug).analyze()
        return jsonify({'address':addr,'distance_m':dist}),200
    except Exception as e:
        traceback.print_exc()
        return jsonify({'error':'Analysis failed','details':str(e)}),500

@app.route('/parse', methods=['GET'])
def parse_labels():
    slug=request.args.get('slug','').strip()
    view=request.args.get('view','street').strip().lower()
    if not slug or view not in ('street','satellite'):
        return jsonify({'error':'slug & view required'}),400
    suffix='1' if view=='street' else '2'
    img_p=os.path.join(app.static_folder,'train','images',f"{slug}-{suffix}.jpg")
    lbl_p=os.path.join(app.static_folder,'train','labels',f"{slug}-{suffix}.txt")
    try:
        res=LabelParser(img_p,lbl_p).parse()
        mapped={
            'polygons': res['polygons'],
            'door': res['door'],
            'curb': res['curb'],
        }
        mapped['route']=PathPlanner.shortest_path(
            tuple(mapped['curb']), tuple(mapped['door']),
            mapped['polygons']['walkways'], mapped['polygons']['obstacle']
        )
        return jsonify(mapped),200
    except Exception as e:
        traceback.print_exc()
        return jsonify({'error':'Parse failed','details':str(e)}),500

if __name__=='__main__':
    app.run(debug=DEBUG,port=5000)
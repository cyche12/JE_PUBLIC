/**
 * OverlayHandler.js
 *
 * - drawItems: paints all Roboflow classes (building, walkways, boundaryof property, obstacle, door, curb)
 * - drawRoute: paints the blue curb→door path
 */
function drawItems(data) {
  const img    = document.getElementById('preview');
  const canvas = document.getElementById('overlay');
  const ctx    = canvas.getContext('2d');
  canvas.width  = img.clientWidth;
  canvas.height = img.clientHeight;
  const sx = img.clientWidth  / img.naturalWidth;
  const sy = img.clientHeight / img.naturalHeight;
  const mapPt = ([x, y]) => [ x * sx, y * sy ];

  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // Filled: building (grey), walkways (green)
  [
    { key: 'building', fill: 'rgba(128,128,128,0.3)' },
    { key: 'walkways', fill: 'rgba(0,200,0,0.3)' }
  ].forEach(({ key, fill }) => {
    ctx.fillStyle = fill;
    (data.polygons[key] || []).forEach(poly => {
      ctx.beginPath();
      poly.forEach(([x, y], i) => {
        const [X, Y] = mapPt([x, y]);
        i === 0 ? ctx.moveTo(X, Y) : ctx.lineTo(X, Y);
      });
      ctx.closePath();
      ctx.fill();
    });
  });

  // Outlined: boundaryof property (yellow), obstacle (red)
  [
    { key: 'boundaryof property', stroke: 'yellow', lineWidth: 2 },
    { key: 'obstacle',             stroke: 'red',    lineWidth: 2 }
  ].forEach(({ key, stroke, lineWidth }) => {
    ctx.strokeStyle = stroke;
    ctx.lineWidth   = lineWidth;
    (data.polygons[key] || []).forEach(poly => {
      ctx.beginPath();
      poly.forEach(([x, y], i) => {
        const [X, Y] = mapPt([x, y]);
        i === 0 ? ctx.moveTo(X, Y) : ctx.lineTo(X, Y);
      });
      ctx.closePath();
      ctx.stroke();
    });
  });

  // Centroids: door (orange), curb (purple)
  [
    { key: 'door', color: 'orange' },
    { key: 'curb', color: 'purple' }
  ].forEach(({ key, color }) => {
    const pt = data[key];
    if (!pt) return;
    const [X, Y] = mapPt(pt);
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.arc(X, Y, 5, 0, 2 * Math.PI);
    ctx.fill();
  });
}

function drawRoute(data) {
  const img    = document.getElementById('preview');
  const canvas = document.getElementById('overlay');
  const ctx    = canvas.getContext('2d');
  canvas.width  = img.clientWidth;
  canvas.height = img.clientHeight;
  const sx = img.clientWidth  / img.naturalWidth;
  const sy = img.clientHeight / img.naturalHeight;
  const mapPt = ([x, y]) => [ x * sx, y * sy ];

  const route = data.route || [];
  if (route.length < 2) return;

  ctx.strokeStyle = 'blue';
  ctx.lineWidth   = 3;
  ctx.beginPath();
  route.forEach(([x, y], i) => {
    const [X, Y] = mapPt([x, y]);
    i === 0 ? ctx.moveTo(X, Y) : ctx.lineTo(X, Y);
  });
  ctx.stroke();
}

window.OverlayHandler = {
  drawItems,
  drawRoute
};

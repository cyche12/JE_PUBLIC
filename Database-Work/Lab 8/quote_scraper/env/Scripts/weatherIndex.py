import requests
from bs4 import BeautifulSoup
import pymongo
from datetime import datetime, timezone


client = pymongo.MongoClient('mongodb://localhost:27017/')
db = client['weather_db']
collection = db['weather_data']

url = 'https://www.weather.gc.ca/canada_e.html'
response = requests.get(url)
soup = BeautifulSoup(response.text, 'lxml')


table = soup.find('table')
headers = [header.get_text(strip=True) for header in table.find_all('th')]

rows = []
for tr in table.find_all('tr')[1:]:  # Skip header row
    cells = tr.find_all('td')
    if len(cells) > 0:
        row_data = {headers[i]: cells[i].get_text(strip=True) for i in range(len(cells))}
        rows.append(row_data)

for row in rows:
    row['last_modified'] = datetime.now(tz=timezone.utc)

if rows:
    collection.insert_many(rows)
    print(f"Inserted {len(rows)} rows into MongoDB.")

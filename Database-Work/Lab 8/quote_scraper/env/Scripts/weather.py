import requests
from bs4 import BeautifulSoup

url = 'https://www.weather.gc.ca/canada_e.html'
response = requests.get(url)


soup = BeautifulSoup(response.text, 'html.parser')

table = soup.find('table')
headers = [th.get_text(strip=True) for th in table.find_all('th')]
print("Table headers:", headers)

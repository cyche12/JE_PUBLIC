import requests
from bs4 import BeautifulSoup
from pymongo import MongoClient
from datetime import datetime

# MongoDB setup
client = MongoClient('localhost', 27017)  # Adjust connection parameters if necessary
db = client['CST8276']  # Database name
collection = db['quotes']  # Collection name

# Make a request to the site and get it as a string
url = 'http://quotes.toscrape.com/'
markup = requests.get(url).text

# Pass the string to a BeautifulSoup object
soup = BeautifulSoup(markup, 'html.parser')

# This will hold all the quotes
quotes = []

# Now we can select elements
for item in soup.select('.quote'):
    quote = {
        "text": item.select_one('.text').get_text(),
        "author": item.select_one('.author').get_text(),
        "tags": [tag.get_text() for tag in item.select('.tags .tag')],
        "Last_updated": datetime.utcnow(),  # Add the current UTC timestamp
        "Source": url  # Add the source URL
    }
    quotes.append(quote)
    
    # Insert each quote into MongoDB
    collection.insert_one(quote)

print(f"Inserted {len(quotes)} quotes into the MongoDB collection.")

import urllib.request
import urllib.parse
with urllib.request.urlopen('https://aiohttpp.herokuapp.com/ajax', urllib.parse.urlencode({'table':'productUnit', 'limit':'all', 'offset':0}).encode('ascii')) as f:
    print(f.read().decode('utf-8'))

#!/usr/bin/python

import sys, MySQLdb as mdb, base64

# Gets all bands from the database
def getAllBands():
    con = mdb.connect('localhost', 'root', '', '123PLEASANTST');
    with con:
        cur = con.cursor()
        cur.execute("SELECT * FROM BAND")
        rows = cur.fetchall()
    if con:    
        con.close()
    return rows

# Finds all bands that match a string
def findBand(name):
    names = ""
    decodedName=""
    bands = getAllBands()
    for band in bands:
        decodedName = base64.b64decode(band[1])
        if name.upper() in decodedName.upper():
            names = names + decodedName
    return names
    



def main(argv):

    print "Hello"

if __name__ == "__main__":
    main(sys.argv[1:])

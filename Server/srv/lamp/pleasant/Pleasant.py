#!/usr/bin/python

import sys, MySQLdb as mdb, base64

# Gets all bands from the database before of after today
def getAllBands(beforeToday):
    con = mdb.connect('localhost', 'root', '', '123PLEASANTST');
    with con:
        cur = con.cursor()
        if beforeToday == True:
            cur.execute("SELECT NAME FROM BAND JOIN EVENT ON BAND.ID = EVENT.BAND1 OR BAND.ID = EVENT.BAND2 OR BAND.ID = EVENT.BAND3 OR BAND.ID = EVENT.BAND4 OR BAND.ID = EVENT.BAND5 OR BAND.ID = EVENT.BAND6 OR BAND.ID = EVENT.BAND7 OR BAND.ID = EVENT.BAND8 OR BAND.ID = EVENT.BAND9 WHERE EVENTDATE < CURDATE()")
        else:
            cur.execute("SELECT NAME FROM BAND JOIN EVENT ON BAND.ID = EVENT.BAND1 OR BAND.ID = EVENT.BAND2 OR BAND.ID = EVENT.BAND3 OR BAND.ID = EVENT.BAND4 OR BAND.ID = EVENT.BAND5 OR BAND.ID = EVENT.BAND6 OR BAND.ID = EVENT.BAND7 OR BAND.ID = EVENT.BAND8 OR BAND.ID = EVENT.BAND9 WHERE EVENTDATE > CURDATE()")
        rows = cur.fetchall()
    if con:    
        con.close()
    return rows

# Finds all bands that match a string
def findBand(name, beforeToday):
    names = ""
    decodedName=""
    bands = getAllBands(beforeToday)
    for band in bands:
        decodedName = base64.b64decode(band[0])
        if name.upper() in decodedName.upper():
            names = names + decodedName
    return names
    
def main(argv):

    print "This program was not ment to be called directly"

if __name__ == "__main__":
    main(sys.argv[1:])

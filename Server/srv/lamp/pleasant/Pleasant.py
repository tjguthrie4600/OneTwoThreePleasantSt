#!/usr/bin/python

import sys, MySQLdb as mdb, base64

# Given a statement queries the database
def query (statement):
    con = mdb.connect('localhost', 'root','','123PLEASANTST')
    with con:
        cur = con.cursor()
        cur.execute(statement)
        rows = cur.fetchall()
    if con:
        con.close()
    return rows

# Gets all bands from the database before of after today
def getAllBands(beforeToday):
    if beforeToday == True:
        rows = query("SELECT NAME FROM BAND JOIN EVENT ON BAND.ID = EVENT.BAND1 OR BAND.ID = EVENT.BAND2 OR BAND.ID = EVENT.BAND3 OR BAND.ID = EVENT.BAND4 OR BAND.ID = EVENT.BAND5 OR BAND.ID = EVENT.BAND6 OR BAND.ID = EVENT.BAND7 OR BAND.ID = EVENT.BAND8 OR BAND.ID = EVENT.BAND9 WHERE EVENTDATE < CURDATE()")
    else:
        rows = query("SELECT NAME FROM BAND JOIN EVENT ON BAND.ID = EVENT.BAND1 OR BAND.ID = EVENT.BAND2 OR BAND.ID = EVENT.BAND3 OR BAND.ID = EVENT.BAND4 OR BAND.ID = EVENT.BAND5 OR BAND.ID = EVENT.BAND6 OR BAND.ID = EVENT.BAND7 OR BAND.ID = EVENT.BAND8 OR BAND.ID = EVENT.BAND9 WHERE EVENTDATE > CURDATE()")    
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

# Gets the bandID from the band name
def getBandID(name):
    encodedName = base64.b64encode(name + "\n")
    bandID = query("SELECT ID FROM BAND WHERE NAME = '" + encodedName + "'")
    if bandID:
        bandID = bandID[0][0]
    return bandID
    
# Gets the bands web site
def getBandWeb(name):
    encodedName = base64.b64encode(name + "\n")
    url = query("SELECT URL FROM BAND WHERE NAME ='" + encodedName + "'")
    if url and url[0][0] is not None:
        decodedURL = base64.b64decode(url[0][0])
    else:
        decodedURL="There is no website associated with this band\n"
    return decodedURL

# Gets dates band has played
def getDates(name):
    dateString = ""
    bandID = getBandID(name)
    dates = query("SELECT EVENTDATE FROM EVENT WHERE BAND1 =" + str(bandID) + " OR BAND2 =" + str(bandID) + " OR BAND3 =" + str(bandID) + " OR BAND4 =" + str(bandID) + " OR BAND5 =" + str(bandID) + " OR BAND6 =" + str(bandID) + " OR BAND7 =" + str(bandID) + " OR BAND8 =" + str(bandID) + " OR BAND9 =" + str(bandID))
    for date in dates:
        dateString = dateString + str(date[0]) + "\n"
    return dateString

def getBandsFromDate(date):
    result=""
    bands = query("SELECT NAME FROM BAND JOIN EVENT ON BAND.ID = EVENT.BAND1 OR BAND.ID = EVENT.BAND2 OR BAND.ID = EVENT.BAND3 OR BAND.ID = EVENT.BAND4 OR BAND.ID = EVENT.BAND5 OR BAND.I\
D = EVENT.BAND6 OR BAND.ID = EVENT.BAND7 OR BAND.ID = EVENT.BAND8 OR BAND.ID = EVENT.BAND9 WHERE EVENTDATE ='" + str(date) + "'")
    for band in bands:
        result = result + base64.b64decode(band[0])
    return result

def getComment(date):
    result = ""
    comment = query("SELECT COMMENT FROM EVENT WHERE EVENTDATE='" + date + "'")
    if comment:
        if comment[0][0]:
            result = base64.b64decode(comment[0][0])
        else:
            result = "NULL"
    else:
        result = "NULL"
    return result

def getTicket(date):
    result = ""
    ticket = query("SELECT TICKET FROM EVENT WHERE EVENTDATE='" + date + "'")
    if ticket:
        if ticket[0][0]:
            result = base64.b64decode(ticket[0][0])
        else:
            result = "NULL"
    else:
        result = "NULL"
    return result

# Given last day of month, returns all days that have events
def getMonthlyEvents(lastDate):
    result = ""
    finalDay = ""
    dateList = lastDate.split("-")
    tempDateList = []
    year = dateList[0]
    month = dateList[1]
    day = dateList[2]
    dates = query("SELECT EVENTDATE FROM EVENT WHERE EVENTDATE BETWEEN '" + year + "-" + month + "-01' and '" + year + "-" + month + "-" + day + "'")
    for date in dates:
        if date[0] is not None:
            tempDateList = str(date[0]).split("-")
            finalDay = tempDateList[2]
            result = result +  finalDay + "\n"
    return result

def main(argv):

    # Get paramaters
    function = int(argv[0])
    statement = str(argv[1])
    result = ""

    # Call functions
    if function == 0:
        result = findBand(statement, True)
    elif function == 1:
        result = findBand(statement, False)
    elif function == 2:
        result = statement + "\n" + getBandWeb(statement) + getDates(statement)
    elif function == 3:
        result = statement + "\n" + getBandsFromDate(statement)
    elif function == 4:
        result = statement + "\n" + getComment(statement) + "\n" + getTicket(statement)
    elif function == 5:
        result = getMonthlyEvents(statement)

    # Hand the result back to the server
    return result

if __name__ == "__main__":
    main(sys.argv[1:])

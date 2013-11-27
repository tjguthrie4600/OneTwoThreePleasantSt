#!/usr/bin/python

import Pleasant, SimpleXMLRPCServer, os, datetime

# Get The Date And Time
now = datetime.datetime.now()

# Open The Logs
f = open('/srv/lamp/pleasant/AccessLog', 'a+')

# Function To Call Mobile Application
def PleasantMobile(band, beforeToday):
    # Make Apache Happy
    print "Content-Type: text/xml\n"
    # Call The Application
    dataStream = Pleasant.findBand(band,bool(int(beforeToday)))
    f.write(now.strftime("%Y-%m-%d %H:%M: ") + dataStream + '\n')
    return dataStream

# CGI SERVER
# ---------------------------------------------------------------------
# Create A Server
server = SimpleXMLRPCServer.CGIXMLRPCRequestHandler()
# Register The Functions The Client Can Call
server.register_function(PleasantMobile)
# Handles The Requests
server.handle_request()
f.close()

f=open('/home/tguthri1/Classes/HCI/Database/working/bands/band', 'r')


for line in f:
    line = line.rstrip()
    line = line.lstrip()
    line = line.upper()
    print line

f=open('/home/tguthri1/Classes/HCI/Database/working/band_dates/case_insensitive_bands', 'r')


for line in f:
    line = line.rstrip()
    line = line.lstrip()
    line = line.upper()
    print line

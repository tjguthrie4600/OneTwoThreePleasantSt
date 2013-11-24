f1=open('/home/tguthri1/Classes/HCI/Database/working/a', 'r')

array = [];

for line in f1:
    array.append(line.split(',')[0])

array.sort(key = lambda s: len(s), reverse=True)

for item in array:
    print item


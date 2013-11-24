import fileinput
import sys

number=0
count=0

for line in fileinput.input(sys.argv[1]):
    X=line.split()
    if len(X) != 0 and (X[0] == "1" or X[0] == "2" or X[0] == "3" or X[0] == "4"  or X[0] == "5" or X[0] == "6" or X[0] == "7" or X[0] == "8" or X[0] == "9" or X[0] == "10" or X[0] == "11" or X[0] == "12" or X[0] == "13" or X[0] == "14" or X[0] == "15" or X[0] == "16" or X[0] == "17" or X[0] == "18" or X[0] == "19" or X[0] == "20" or X[0] == "21" or X[0] == "22" or X[0] == "23" or X[0] == "24" or X[0] == "25" or X[0] == "26" or X[0] == "27" or X[0] == "28" or X[0] == "29" or X[0] == "30" or X[0] == "31"):
        number=1
        Y=X[0]
    else:
        if number == 1:
            count = count + 1
            if count == 4:
                print Y + " " + line
                count = 0
                number = 0

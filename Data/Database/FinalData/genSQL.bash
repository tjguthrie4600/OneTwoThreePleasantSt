#! /bin/bash

bands()
{
    declare -i WEBSITE
    while read line; do

	WEBSITE=`echo $line | awk -F "," '{print NF}'`
	ID=`echo $line | awk -F "," '{print $1}'`
	NAME=`echo $line | awk -F "," '{print $2}'`
	NAME64=`echo $NAME | base64`	

	if [ $WEBSITE == 3 ]; then
	    URL=`echo $line | awk -F "," '{print $3}'`
	    URL64=`echo $URL | base64`
	    echo "INSERT INTO BAND (ID, NAME, URL) VALUES($ID, '$NAME64', '$URL64');"
	else
	    echo "INSERT INTO BAND (ID, NAME) VALUES($ID, '$NAME64');"
	fi

    done < band_table
}


events()
{
    declare -i NUM

    while read line; do

	DATE=`echo $line | cut -f1 -d " "`
	BANDS=`echo $line | awk '{$1=""; print $0}'`

	STATE="INSERT INTO EVENT (EVENTDATE"
	NUM=`echo $line | awk '{print NF-1}'`
	for x in `seq 1 $NUM`; do
	    STATE="$STATE,BAND$x"
	done
	STATE="$STATE) VALUES('$DATE',"
	for y in $BANDS; do
	    STATE="$STATE$y"
	done
	STATE="$STATE);"

	echo $STATE
	STATE=""
    done < event_table
}

comments()
{
    while read line; do
	EVENTDATE=`echo $line | cut -f1 -d " "`
	COMMENT=`echo $line | awk '{$1=""; print $0}'`

	COMMENT64=`echo $COMMENT | base64`

	echo "INSERT INTO EVENT (EVENTDATE,COMMENT) VALUES ('$EVENTDATE','$COMMENT64') ON DUPLICATE KEY UPDATE COMMENT = '$COMMENT64';"
    done < event_comments
}

tickets()
{
    while read line; do
	EVENTDATE=`echo $line | cut -f1 -d " "`
	TICKET=`echo $line | awk '{$1=""; print $0}'`

	TICKET64=`echo $TICKET | base64`

	echo "INSERT INTO EVENT (EVENTDATE,TICKET) VALUES ('$EVENTDATE','$TICKET64') ON DUPLICATE KEY UPDATE TICKET = '$TICKET64';"
    done < event_tickets
}



tickets

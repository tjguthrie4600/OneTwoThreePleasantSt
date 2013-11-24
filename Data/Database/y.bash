X=`cat websites`
Y=`cat band`
found="0"

while read line; do

    while read line1; do
	b=`echo $line1 | cut -f1 -d ,`
	w=`echo $line1 | cut -f2 -d ,`
	if [ "$b" == "$line" ]; then
	    echo "$b,$w"
	    found="1"
	fi
	    
    done < web

    if [ "$found" == "0" ]; then
	echo $line
    fi

    found="0"

done < band

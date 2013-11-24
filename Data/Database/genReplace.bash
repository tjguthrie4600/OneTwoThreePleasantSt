#! /bin/bash

while read line; do
    BAND_ID=`echo $line | awk '{print $1}'`
    BAND=`echo $line | awk '{$1=""; print $0}'`

    echo "sed -i s%\"`echo $BAND`\"%\"$BAND_ID\"%Ig all_bands_nospace"
    
done < f2 

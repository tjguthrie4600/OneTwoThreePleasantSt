#! /bin/bash


for j in {1999..2013}
do
for i in {1..12}
do
    if [ $i -lt 10 ]; then
	X=$j"_0"$i
	wget wget -O $X "http://www.123pleasantstreet.com/calendar/$j/0$i"
    else
	X=$j"_"$i
	wget -O $X "http://www.123pleasantstreet.com/calendar/$j/$i"
    fi
done
done
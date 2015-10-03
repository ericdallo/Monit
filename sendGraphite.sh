#!/bin/bash
 
# Send some random data
 #
 
# Set this hostname
 HOSTNAME=`hostname --short`
 
# Set Graphite host
 GRAPHITE=54.68.38.254
 GRAPHITE_PORT=8125
 
# Loop forever
 while :
 do
 
# Collect some random data for
 # this example
 MY_DATA=$[ ( $RANDOM % 100 )  + 1 ]
 
# Send data to Graphite
echo $MY_DATA
echo -n "teste:${MY_DATA}|c" | nc -w 1 -u $GRAPHITE $GRAPHITE_PORT
#echo -n "teste:$(((RANDOM % 100)+1))|c" | nc -w 1 -u localhost 8125
 
sleep 1
 done
 
# End

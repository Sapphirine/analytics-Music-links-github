#!/bin/bash

	case $1 in
		stop)
			kill -9 $(cat /tmp/RecommenderApp/PID.txt)
			rm /tmp/RecommenderApp/PID.txt
			;;
 		start)
			mkdir -p /tmp/RecommenderApp
			nodejs ../nodejs/app.js &
			echo "$!" >> /tmp/RecommenderApp/PID.txt
			;;
		help)
			echo
			echo "Bad input arguments"
			echo "Input arguments:"
			echo "start      starts the application"
			echo "stop       stops the application"
			;;
		?*)
			echo
			echo "Bad input arguments"
			echo "Input arguments:"
                        echo "start      starts the application"
                        echo "stop       stops the application"
			;;
		"")
			echo
			echo "Bad input arguments"
			echo "Input arguments:"
                        echo "start      starts the application"
                        echo "stop       stops the application"
			;;
	esac

echo

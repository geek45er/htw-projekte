#!/bin/bash


DATUM=$(date +%Y-%m-%d)
REPORT="report_${DATUM}.txt"
exec > "$REPORT" 2>&1


echo "###analysiere logs" 
echo "erstellt am: $(date '+%Y-%m-%d %H:%M:%S')"
echo "==================" 

LOG_PATH="$(cd "$(dirname "$0")" && pwd)"
ERROR_TYPES=("ERROR" "FATAL" "CRITICAL")

LOGFILES=( 
	"${LOG_PATH}/application.log"
	"${LOG_PATH}/system.log"

)

RECENT_LOGFILES=()
for logfile in "${LOGFILES[@]}"; do
	if [ -f "$logfile" ] && [ $(find "$logfile" -mtime -1 2>/dev/null | wc -l) -gt 0 ]; then
		RECENT_LOGFILES+=("$logfile")
	fi
done


if [ ${#RECENT_LOGFILES[@]} -eq 0 ]; then
	echo -e "\nkeine log files geandert in den lettzen 24h"
	exit 0
fi

echo -e "\ngefundene files letzte 24h: ${RECENT_LOGFILES[@]}"


for logfile in "${RECENT_LOGFILES[@]}"; do
	echo -e "\n=========================================="
	echo "### ananlysiere: $(basename $logfile) ###"
	echo "=========================================="

	for error in "${ERROR_TYPES[@]}"; do
		count=$(grep -c "$error" "$logfile" 2>/dev/null)
		echo -e "\n[$error] Treffer: $count"	

		if [ $count -gt 10 ]; then
			echo " achtung, mehr als 10 $error-Meldungen gefunden!" 
		fi


		if [ $count -gt 0 ]; then
			echo "---"
			grep "$error" "$logfile" 2>/dev/null
		fi 
	done 
done

echo -e "\n=========================================="
echo  "### analyse abgeschlossen###"
echo "=========================================="

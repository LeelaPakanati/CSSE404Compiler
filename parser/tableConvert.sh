#!/bin/sh
sed 's/\t/:/g' table.txt | sed 's/^[^:]*:[^:]*://g' | sed -E 's/:[A-z]* -> /:/g' | sed -E 's/([^:]+)/"\1"/g' | sed 's/:/:null/g' | sed 's/:null"/:"/g' | sed -E 's/^([^:]+):/this.table.put(\1, new String[] {/g' | sed 's/$/});/g' | sed 's/:/,/g'

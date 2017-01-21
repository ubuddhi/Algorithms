__author__ = 'Udayasri'

import sys

inputfilename = sys.argv[1]#reads the firs argument(file name)
inputfile = open(inputfilename)#opens the input file name
data = inputfile.read()#reading the contents from the input file
output = inputfilename.split(".")#splits the filename with dot operator
outputfilename = output[0]+".lzw" # edits the file name with .lzw
outputfile = open(outputfilename, 'w',encoding="UTF-16")#opens the file in write mode with UTF-16 encoding
N=sys.argv[2] #reading the second argument

MAX_TABLE_SIZE = 2**int(N) #declaring the maximum size for the table

table = dict((chr(i),i) for i in range(255)) #initialized the table from 0-255
string = "" #initializing the varable string with null string
for symbol in data: #for the symbols in the input data
        if (string + symbol) in table.keys(): #checking if the new string is already in the table
            string = string + symbol
        else:
            result = str(table[string]) # converts the integer to string
            print(result) #prints the result on the terminal
            result.encode(encoding='UTF-16', errors='strict') # encoding the result with UTF-16
            outputfile.write(result+" ") #writing the output to the file

            if len(table) < MAX_TABLE_SIZE: #checking if the table size is not exceeding the maximum table size declared
                table[string + symbol] = len(table)+1 # adding the new string to the table
            string = symbol
result = str(table[string]) #converting the result to  string
print(result)# prints the result on the terminal
result.encode(encoding='UTF-16', errors='strict')# encoding the result with UTF-16
outputfile.write(result) # writing the data
inputfile.close()  #closing input file
outputfile.close() #closing output file z   A



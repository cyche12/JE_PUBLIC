      ******************************************************************
      * Author:        Jake Elliott                                    *
      * Course:        CST8283 Section 302                             *
      * Date:           26/07/25                                     *
      * Program ID:    CREATE-IDX-STOCKS                               *
      ******************************************************************
       IDENTIFICATION DIVISION.
       PROGRAM-ID. CREATE-IDX-STOCKS.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT STOCKS-IN  ASSIGN TO "..\STOCKS.txt"
               ORGANIZATION IS LINE SEQUENTIAL.
           SELECT STOCKS-IDX ASSIGN TO "..\STOCKS-IDX.dat"
               ORGANIZATION IS INDEXED
               ACCESS MODE  IS SEQUENTIAL
               RECORD KEY   IS STOCK-SYMBOL
               ALTERNATE RECORD KEY IS INVESTMENT-SECTOR WITH DUPLICATES
               FILE STATUS  IS IDX-STATUS.

       DATA DIVISION.
       FILE SECTION.
       FD  STOCKS-IN.
       01  STOCKS-IN-REC.
           05 IN-STOCK-SYMBOL PIC X(7).
           05 IN-STOCK-NAME PIC X(25).
           05 IN-CLOSING-PRICE-RAW PIC 9(6).
           05 IN-INVESTMENT-SECTOR PIC X(20).

       FD  STOCKS-IDX.
       01  STOCKS-IDX-REC.
           05 STOCK-SYMBOL PIC X(7).
           05 STOCK-NAME PIC X(25).
           05 CLOSING-PRICE PIC 9(4)V99.
           05 INVESTMENT-SECTOR PIC X(20).

       WORKING-STORAGE SECTION.
       01 EOF-FLAG PIC X VALUE "N".
          88 EOF-YES VALUE "Y".
          88 EOF-NO VALUE "N".
       01 IDX-STATUS PIC XX VALUE SPACES.
       01 WS-PRICE PIC 9(4)V99 VALUE 0.
       01 WRITE-CTR PIC 9(4) VALUE 0.
       01 SKIP-CTR PIC 9(4) VALUE 0.

       PROCEDURE DIVISION.
       MAIN-PROCEDURE.
            PERFORM 201-INITIALIZE-RTN
            PERFORM 202-PROCESS-RTN UNTIL EOF-YES
            PERFORM 203-TERMINATE-RTN
            STOP RUN.

       201-INITIALIZE-RTN.
            OPEN INPUT  STOCKS-IN
            OPEN OUTPUT STOCKS-IDX
            MOVE "N" TO EOF-FLAG
            EXIT.

       202-PROCESS-RTN.
           PERFORM 203-READ-INPUT-RTN
           IF EOF-NO
           PERFORM 204-UPDATE-INDEX
           PERFORM 301-WRITE-OUTPUT-RTN
           END-IF
           EXIT.

       203-READ-INPUT-RTN.
           READ STOCKS-IN
           AT END
           SET EOF-YES TO TRUE
           END-READ
           EXIT.

       204-UPDATE-INDEX.
           MOVE IN-STOCK-SYMBOL TO STOCK-SYMBOL
           MOVE IN-STOCK-NAME TO STOCK-NAME
           MOVE IN-INVESTMENT-SECTOR TO INVESTMENT-SECTOR
           COMPUTE WS-PRICE = IN-CLOSING-PRICE-RAW / 100
           MOVE WS-PRICE TO CLOSING-PRICE
           EXIT.

       301-WRITE-OUTPUT-RTN.
           WRITE STOCKS-IDX-REC
           INVALID KEY
           ADD 1 TO SKIP-CTR
           NOT INVALID KEY
           ADD 1 TO WRITE-CTR
           END-WRITE
           EXIT.

       203-TERMINATE-RTN.
           CLOSE STOCKS-IN STOCKS-IDX
           DISPLAY "RECORDS WRITTEN: " WRITE-CTR
           DISPLAY "RECORDS SKIPPED: " SKIP-CTR
           EXIT.

       END PROGRAM CREATE-IDX-STOCKS.

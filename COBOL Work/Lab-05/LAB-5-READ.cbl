      ******************************************************************
      * Author:        Jake Elliott                                    *
      * Course:        CST8283 Section 302                             *
      * Date:          11/08/2025                                      *
      * Program ID:    LIST-STOCKS-BY-SECTOR                           *
      * Purpose:       Read all records for a given INVESTMENT SECTOR  *
      *                using alternate key + START + READ NEXT         *
      ******************************************************************
       IDENTIFICATION DIVISION.
       PROGRAM-ID. LIST-STOCKS-BY-SECTOR.

       ENVIRONMENT DIVISION.
       INPUT-OUTPUT SECTION.
       FILE-CONTROL.
           SELECT STOCKS-IDX ASSIGN TO "..\STOCKS-IDX.dat"
               ORGANIZATION IS INDEXED
               ACCESS MODE  IS DYNAMIC
               RECORD KEY   IS STOCK-SYMBOL
               ALTERNATE RECORD KEY IS INVESTMENT-SECTOR WITH DUPLICATES
               FILE STATUS  IS IDX-STATUS.

       DATA DIVISION.
       FILE SECTION.
       FD  STOCKS-IDX.
       01  STOCKS-IDX-REC.
           05 STOCK-SYMBOL          PIC X(7).
           05 STOCK-NAME            PIC X(25).
           05 CLOSING-PRICE         PIC 9(4)V99.
           05 INVESTMENT-SECTOR     PIC X(20).

       WORKING-STORAGE SECTION.
       01 IDX-STATUS                PIC XX VALUE SPACES.

       01 EOF-FLAG                  PIC X VALUE "N".
          88 EOF-YES                VALUE "Y".
          88 EOF-NO                 VALUE "N".

       01 FOUND-FLAG                PIC X VALUE "N".
          88 FOUND-ANY              VALUE "Y".
          88 FOUND-NONE             VALUE "N".

       01 WS-INPUT-SECTOR           PIC X(20) VALUE SPACES.
       01 WS-SEARCH-SECTOR          PIC X(20) VALUE SPACES.

       01 REC-COUNT                 PIC 9(4) VALUE 0.
       01 DISP-PRICE                PIC ZZ,ZZ9.99.

       PROCEDURE DIVISION.
       MAIN-PROCEDURE.
           PERFORM 201-INITIALIZE-RTN
           PERFORM 202-GET-INPUT-RTN
           IF WS-SEARCH-SECTOR = SPACES
              PERFORM 205-TERMINATE-RTN
              STOP RUN
           END-IF
           PERFORM 301-START-SECTOR-RTN
           IF EOF-YES
              DISPLAY "No records found for sector."
              PERFORM 205-TERMINATE-RTN
              STOP RUN
           END-IF
           PERFORM 302-DISPLAY-HEADER-RTN
           PERFORM 303-READ-NEXT-LOOP-RTN UNTIL EOF-YES
           IF FOUND-NONE
              DISPLAY "No records found for sector."
           ELSE
              DISPLAY "Total records: " REC-COUNT
           END-IF
           PERFORM 205-TERMINATE-RTN
           STOP RUN.

       201-INITIALIZE-RTN.
           OPEN INPUT STOCKS-IDX
           MOVE "N" TO EOF-FLAG
           MOVE "N" TO FOUND-FLAG
           MOVE 0    TO REC-COUNT
           EXIT.

       202-GET-INPUT-RTN.
           DISPLAY "Enter INVESTMENT SECTOR (exact, up to 20 chars):"
           ACCEPT WS-INPUT-SECTOR
           MOVE WS-INPUT-SECTOR TO WS-SEARCH-SECTOR
           EXIT.

      * Position to the first record >= desired sector using the ALT key
       301-START-SECTOR-RTN.
           MOVE WS-SEARCH-SECTOR TO INVESTMENT-SECTOR
           START STOCKS-IDX KEY IS >= INVESTMENT-SECTOR
               INVALID KEY
                   SET EOF-YES TO TRUE
           END-START
           EXIT.

      * After START, iterate with READ NEXT; stop when sector changes
       303-READ-NEXT-LOOP-RTN.
           READ STOCKS-IDX NEXT RECORD
               AT END
                   SET EOF-YES TO TRUE
               NOT AT END
                   IF INVESTMENT-SECTOR = WS-SEARCH-SECTOR
                      PERFORM 304-DISPLAY-RECORD-RTN
                      SET FOUND-ANY TO TRUE
                   ELSE
                      SET EOF-YES TO TRUE
                   END-IF
           END-READ
           EXIT.

       302-DISPLAY-HEADER-RTN.
           DISPLAY "SYMBOL  NAME   PRICE     SECTOR"
           EXIT.

       304-DISPLAY-RECORD-RTN.
           MOVE CLOSING-PRICE TO DISP-PRICE
           DISPLAY STOCK-SYMBOL "  "
                   STOCK-NAME   "  "
                   DISP-PRICE   "  "
                   INVESTMENT-SECTOR
           ADD 1 TO REC-COUNT
           EXIT.

       205-TERMINATE-RTN.
           CLOSE STOCKS-IDX
           EXIT.

       END PROGRAM LIST-STOCKS-BY-SECTOR.

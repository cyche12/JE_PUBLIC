      ******************************************************************
      * Author:JAKE ELLIOTT
      * Date:6/17/2025
      * Purpose: ACCEPTS A POSITIVE INTEGER WHICH IS LESS THAN OR EQUAL
      * TO 20, CALCULATES AND DISPLAYS THE FIBONACCI NUMBER.
      * Tectonics: cobc
      ******************************************************************
       IDENTIFICATION DIVISION.
       PROGRAM-ID. LAB-3-FIBONACCI-SEQ.
       DATA DIVISION.
       FILE SECTION.
       WORKING-STORAGE SECTION.

       01 WS-N PIC 99 VALUE 0.
       01 WS-INDEX PIC 99 VALUE 0.
       01 WS-FIB-1 PIC 9(9) VALUE 1.
       01 WS-FIB-2 PIC 9(9) VALUE 1.
       01 WS-FIB-N PIC 9(9) VALUE 0.
       01 WS-FIB-DISPLAY PIC Z(9).
       01 WS-TEMP-N PIC 99 VALUE 0.

       PROCEDURE DIVISION.
       100-MAIN-PROCEDURE.
           PERFORM 200-READ-USER-INPUT
           PERFORM 201-VALIDATE-INPUT
           PERFORM 202-CHECK-BASE
           PERFORM 300-CALCULATE-SEQ
           PERFORM 203-DISPLAY-SEQ
           STOP RUN.

       200-READ-USER-INPUT.
           DISPLAY "ENTER A NUMBER BETWEEN 1-20: " WITH NO ADVANCING
           ACCEPT WS-N.

       201-VALIDATE-INPUT.
           IF WS-N < 0 OR WS-N > 20
               DISPLAY "INVALID INPUT, PLEASE ENTER A NUMBER FROM 1-20"
               STOP RUN
           END-IF.

       202-CHECK-BASE.
           IF WS-N = 1 OR WS-N = 2
               MOVE 1 TO WS-FIB-N
               ELSE MOVE 3 TO WS-INDEX
           END-IF.

       203-DISPLAY-SEQ.
           MOVE WS-FIB-N TO WS-FIB-DISPLAY
           DISPLAY "FIBONACCI NUMBER F('N') IS: " WS-FIB-DISPLAY.

       300-CALCULATE-SEQ.
           IF WS-N > 2
               PERFORM 301-START-FIB-SEQ
           END-IF.

       301-START-FIB-SEQ.
           PERFORM 302-FIB-LOOP VARYING WS-INDEX FROM 3 BY 1
           UNTIL WS-INDEX > WS-N.

       302-FIB-LOOP.
           COMPUTE WS-FIB-N = WS-FIB-1 + WS-FIB-2
           MOVE WS-FIB-2 TO WS-FIB-1
           MOVE WS-FIB-N TO WS-FIB-2.

       END PROGRAM LAB-3-FIBONACCI-SEQ.

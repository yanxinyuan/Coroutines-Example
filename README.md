
The example of Coroutines in java was using https://github.com/offbynull/coroutines

### Step 1
mvn clean install

### Step 2
java -jar target\Coroutine-Test-0.0.1-SNAPSHOT.jar

The result might be this.
#### 
    initiate 1 tasks  
    initiate 2 tasks  
    initiate 3 tasks  
    1 start doing something  
    1 is doing now  
    1 start suspend  
    initiate 4 tasks  
    2 start doing something  
    2 is doing now  
    2 start suspend  
    3 start doing something  
    3 is doing now  
    3 start suspend  
    4 start doing something  
    4 is doing now  
    4 start suspend  
    1 recover and finish  
    2 is doing again  
    2 start suspend again  
    initiate 5 tasks  
    5 start doing something  
    5 is doing now  
    5 start suspend  
    3 recover and finish  
    4 is doing again  
    4 start suspend again  
    2 recover and finish  
    5 recover and finish  
    4 recover and finish  

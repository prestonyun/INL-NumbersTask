# INL-NumbersTask
Tests working memory and response time. Participants are required to press a response key when three even or odd numbers are displayed sequentially.

# Details

The delay between each displayed number can be edited in the BackEnd.java file.

Sequences are contained in groups of 5 numbers, and 40% of groups contain a 3-in-a-row even or odd sequence. Each program runs for 12 minutes, after a 15 second countdown.

In the examples folder, there are three executable jar files:

> 1000ms: 144 groups, 58 valid sequences

> 800ms: 180 groups, 72 valid sequences

> 600ms: 240 groups, 96 valid sequences

# Sceenshots

![image](https://user-images.githubusercontent.com/40635145/192858935-94193462-1a76-4597-a877-b2481f18ca66.png)

# How it Works

Since this is a base test for cognition, it must be standardized. Therefore, the task must be randomized so there can be no preparation. It must also have a predetermined number of correct sequences, and it must run for a predetermined amount of time. 

My solution was to create sequences off even/odd numbers that are 5 numbers in length, and connect them such that there will never be false-positives (sequences of 3 odd or even that occur due to randomization, and are not accounted for in the scoring). This came out to 10 target sequences (including 3-in-a-row), and 15 non-target sequences (not including 3-in-a-row).

> Target Sequences: "OOOEE", "OOOEO", "EOOOE", "EEOOO", "OEOOO", "EEEOO", "EEEOE", "OEEEO", "OOEEE", "EOEEE"

> Non-Target Sequences: "OEOEO", "OOEOE", "OEOOE", "OEEOO", "OOEEO", "OOEOO", "OEEOE", "OEOEE", "EOEOE", "EEOEO", "EOEEO", "EOOEE", "EEOEE", "EEOOEE", "EOOEO"

A random selection of target sequences is selcted, in quantity according to the test proctor, and added to an ArrayList. Non-target sequences are then added such that the total runtime will be 12 minutes. The ArrayList is then shuffled, and the ordering is checked to ensure the absence of false-positives. If a false-positive exists, the sequence is simply replaced with another sequence that does not create a false-positive. Finally, a method is called to convert the characters 'O' and 'E' to random odd and even integers, respectively.

During the test, the user is rewarded for responding to correct sequences of 3, and punished for responding to incorrect sequences of 3. This incentive comes in the form of a dollar value that is displayed on screen: $0.12 for a correct response, and -$0.12 for incorrect, to a minimum of $0.00. Their response time is also recorded, and outputted to a text file along with the final test results.

Simple JFrames and ActionListeners are used to handle the UI.


# Credit

Created by Preston Yun for the Integrative Neuroscience Laboratory at Southern Illinois University Carbondale.

prestonyun24@gmail.com

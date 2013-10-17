var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":27,"id":515,"methods":[{"el":20,"sc":5,"sl":16},{"el":24,"sc":5,"sl":22}],"name":"MessageStartGame","sl":11}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_17":{"methods":[{"sl":16}],"name":"selectChat","pass":true,"statements":[{"sl":17},{"sl":18}]},"test_9":{"methods":[{"sl":16}],"name":"startGameSM","pass":true,"statements":[{"sl":17},{"sl":18}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [17, 9], [17, 9], [17, 9], [], [], [], [], [], [], [], [], []]

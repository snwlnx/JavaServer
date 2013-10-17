var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":33,"id":483,"methods":[{"el":24,"sc":5,"sl":21},{"el":28,"sc":5,"sl":26}],"name":"MessageGetAvailableGameSession","sl":16}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_32":{"methods":[{"sl":21}],"name":"authorized","pass":true,"statements":[{"sl":22},{"sl":23}]},"test_51":{"methods":[{"sl":21}],"name":"availableGames","pass":true,"statements":[{"sl":22},{"sl":23}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [51, 32], [51, 32], [51, 32], [], [], [], [], [], [], [], [], [], []]

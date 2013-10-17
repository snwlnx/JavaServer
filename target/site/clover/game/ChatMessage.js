var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":32,"id":633,"methods":[{"el":17,"sc":5,"sl":13},{"el":23,"sc":5,"sl":19},{"el":28,"sc":5,"sl":25}],"name":"ChatMessage","sl":6}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_10":{"methods":[{"sl":13},{"sl":19}],"name":"cChatMessagesTest","pass":true,"statements":[{"sl":14},{"sl":15},{"sl":16},{"sl":20},{"sl":22}]},"test_69":{"methods":[{"sl":13},{"sl":19}],"name":"updateGameStep","pass":true,"statements":[{"sl":14},{"sl":15},{"sl":16},{"sl":20},{"sl":22}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [10, 69], [10, 69], [10, 69], [10, 69], [], [], [10, 69], [10, 69], [], [10, 69], [], [], [], [], [], [], [], [], [], []]

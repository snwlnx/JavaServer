var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":30,"id":1435,"methods":[{"el":19,"sc":5,"sl":16},{"el":24,"sc":5,"sl":21},{"el":29,"sc":5,"sl":26}],"name":"UserDataSetTest","sl":14}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_75":{"methods":[{"sl":26}],"name":"getId","pass":true,"statements":[{"sl":28}]},"test_82":{"methods":[{"sl":21}],"name":"getName","pass":true,"statements":[{"sl":23}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [82], [], [82], [], [], [75], [], [75], [], []]

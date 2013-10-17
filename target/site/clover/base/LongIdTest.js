var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":36,"id":1406,"methods":[{"el":19,"sc":2,"sl":8},{"el":27,"sc":2,"sl":21},{"el":34,"sc":2,"sl":29}],"name":"LongIdTest","sl":6}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_15":{"methods":[{"sl":8}],"name":"equalsTest","pass":true,"statements":[{"sl":10},{"sl":11},{"sl":12},{"sl":14},{"sl":15},{"sl":16},{"sl":18}]},"test_25":{"methods":[{"sl":21}],"name":"hashCodeTest","pass":true,"statements":[{"sl":23},{"sl":24},{"sl":26}]},"test_35":{"methods":[{"sl":29}],"name":"toStringTest","pass":true,"statements":[{"sl":31},{"sl":33}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [15], [], [15], [15], [15], [], [15], [15], [15], [], [15], [], [], [25], [], [25], [25], [], [25], [], [], [35], [], [35], [], [35], [], [], []]

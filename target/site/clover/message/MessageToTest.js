var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":58,"id":1716,"methods":[{"el":37,"sc":5,"sl":32},{"el":43,"sc":2,"sl":39},{"el":50,"sc":2,"sl":45},{"el":57,"sc":2,"sl":52}],"name":"MessageToTest","sl":23}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_37":{"methods":[{"sl":45}],"name":"toFrontendTest","pass":true,"statements":[{"sl":47},{"sl":48},{"sl":49}]},"test_65":{"methods":[{"sl":39}],"name":"getFromTest","pass":true,"statements":[{"sl":41},{"sl":42}]},"test_79":{"methods":[{"sl":52}],"name":"messageGameStep","pass":true,"statements":[{"sl":54}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [65], [], [65], [65], [], [], [37], [], [37], [37], [37], [], [], [79], [], [79], [], [], [], []]

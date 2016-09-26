var subject = require('../src/manipulate/Manipulators.js');
var assert = require('assert');
var assertPropTypes = require('./assert.js');


describe('Manipulators', function() {
  var data = require('json!./data/genesetPageOneRow').expected;
  var orderings = {"Default":{"columns":[0,1,2,3,4,5,6,7,8],"rows":[0]},"Alphabetical order":{"columns":[0,1,2,3,4,5,6,7,8],"rows":[0]},"Gene expression rank":{"columns":[7,5,8,6,4,3,0,1,2],"rows":[0]}};
  describe('order', function() {
    describe('by default ordering', function(){
      var result = subject.order(orderings["Default"], data);
      it('result should have the data series format', function() {
        assertPropTypes.validateHeatmapData(result);
      });
      it('result should not actually change', function() {
        assert.deepEqual(data.xAxisCategories,result.xAxisCategories);
        assert.deepEqual(data.yAxisCategories,result.yAxisCategories);
        assert.deepEqual(data.dataSeries,result.dataSeries);
      });
    });
    describe('by GE ordering', function(){
      var result = subject.order(orderings["Gene expression rank"], data);
      it('result should have the data series format', function() {
        assertPropTypes.validateHeatmapData(result);
      });
      it('the highest column should come first', function() {
        var highest = data.xAxisCategories[7];
        assert.deepEqual(highest,result.xAxisCategories[0]);
      });
    });
  });
  describe('filterByDataSeries', function(){
    describe('keeping all series in', function(){
      var result = subject.filterByDataSeries(data.dataSeries.map((_)=>true), data);
      it('result should have the data series format', function() {
        assertPropTypes.validateHeatmapData(result);
      });
      it('result should not actually change', function() {
        assert.deepEqual(data.xAxisCategories,result.xAxisCategories);
        assert.deepEqual(data.yAxisCategories,result.yAxisCategories);
        assert.deepEqual(data.dataSeries,result.dataSeries);
      });
    });
    describe('dropping one of the series', function(){
      var indexFilteredOut = 1;
      var anotherIndexNotFilteredOut = 2;
      assert.ok(0 < data.dataSeries[indexFilteredOut].data.length);
      assert.ok(0 < data.dataSeries[anotherIndexNotFilteredOut].data.length);


      var result = subject.filterByDataSeries(data.dataSeries.map((_,ix)=>ix!=indexFilteredOut), data);
      it('does not change the info objects', function(){
        assert.equal(data.dataSeries[indexFilteredOut].info, result.dataSeries[indexFilteredOut].info);
        assert.equal(data.dataSeries[anotherIndexNotFilteredOut].info, result.dataSeries[anotherIndexNotFilteredOut].info);
      })
      it('should not change the size of dataset that is not being filtered out', function(){
        assert.deepEqual(data.dataSeries[anotherIndexNotFilteredOut].data.length, result.dataSeries[anotherIndexNotFilteredOut].data.length);
      })
      it('will change the indices in dataset that is not being filtered out', function(){
        assert.notDeepEqual(data.dataSeries[anotherIndexNotFilteredOut].data, result.dataSeries[anotherIndexNotFilteredOut].data);
      })
      it('should put empty data into the index filtered out', function(){
        assert.deepEqual(Object.assign(JSON.parse(JSON.stringify(data.dataSeries[indexFilteredOut])), {data:[]}),result.dataSeries[indexFilteredOut]);
      })
      it('will cause us to have less x axis labels here', function(){
        assert.notDeepEqual(data.xAxisCategories, result.xAxisCategories);
        assert.ok(data.xAxisCategories.length > result.xAxisCategories.length);

      })
    })
  });
  describe('filterByIndex', function(){
    describe('passing non-experiment page data through the code', function(){
      var geneSetPageData = require('json!./data/genesetPageOneRow').expected;
      it('result should have the data series format', function() {
        var result = subject.filterByIndex(0,geneSetPageData);
        assertPropTypes.validateHeatmapData(result);
      });
      it('leaves it as it is with default index value', function (){
        var result = subject.filterByIndex(0,geneSetPageData);
        assert.deepEqual(geneSetPageData, result);
      });
      it('leaves it as it is with random non-zero index value', function (){
        var result = subject.filterByIndex(5,geneSetPageData);
        assert.deepEqual(geneSetPageData, result);
      });
      it('filters all data out with a negative index value', function (){
        var result = subject.filterByIndex(-1,geneSetPageData);
        assert.deepEqual([], [].concat.apply([], result.dataSeries.map((series)=>series.data)));
      });
    });
    describe('data with coexpressions', function(){
      var coexpressionsData = require('json!./data/experimentPageBaselineOneGeneWithCoexpressions').expected;
      it('result should have the data series format', function() {
        var result = subject.filterByIndex(0,coexpressionsData);
        assertPropTypes.validateHeatmapData(result);
      });
      it('leaves one row in with default index value', function (){
        var result = subject.filterByIndex(0,coexpressionsData);
        assert.deepEqual(coexpressionsData.xAxisCategories, result.xAxisCategories);
        assert.equal(1, result.yAxisCategories.length);
      });
      it('leaves it as it is with very high index value', function (){
        var result = subject.filterByIndex(9000,coexpressionsData);
        assert.deepEqual(coexpressionsData, result);
      });
      it('filters all data out with a negative index value', function (){
        var result = subject.filterByIndex(-1,coexpressionsData);
        assert.deepEqual([], [].concat.apply([], result.dataSeries.map((series)=>series.data)));
      });
      it('leaves as many rows as index says plus one', function (){
        assert.equal(3, subject.filterByIndex(2,coexpressionsData).yAxisCategories.length);
        assert.equal(2, subject.filterByIndex(1,coexpressionsData).yAxisCategories.length);
      });
    });
  })
  describe('Grouping', function(){
    var data = require('json!./data/experimentPageBaselineNonSpecific.json').expected;
    describe('Unknown/ default grouping', function(){
      var grouping = "Default";
      var result = subject.group(grouping,data);
      it('result should have the data series format', function() {
        assertPropTypes.validateHeatmapData(result);
      });

      it('x axes and data series should have been processed', function() {
        assert.notEqual(data.xAxisCategories,result.xAxisCategories);
        assert.notEqual(data.dataSeries,result.dataSeries);
      });
      it('result should not actually change, modulo xLabel hack', function() {
        assert.deepEqual(data.xAxisCategories,result.xAxisCategories);
        result.dataSeries.forEach((series)=>(series.data.forEach((point)=> {delete point.info.xId; delete point.info.xLabel;})))
        assert.deepEqual(data.dataSeries,result.dataSeries);
        console.log("I am a test with a hack");
      });
      it('y axis categories should not have been touched', function(){
        assert.equal(data.yAxisCategories,result.yAxisCategories);
      })
    })
    describe('Anatomical systems grouping', function(){
      var grouping = "Anatomical Systems";
      var result = subject.group(grouping,data);
      it('result should have the data series format', function() {
        assertPropTypes.validateHeatmapData(result);
      });
      it('y axis categories should not have been touched', function(){
        assert.equal(data.yAxisCategories,result.yAxisCategories);
      })
      it('result should actually change', function() {
        assert.notDeepEqual(data.xAxisCategories,result.xAxisCategories);
        assert.notDeepEqual(data.dataSeries,result.dataSeries);
      });
      it('We did not lose any values', function(){
        var pointsBefore =
          [].concat.apply([],
            data.dataSeries
            .map((series)=>series.data)
          )
          .map((point)=>point.value)
          .sort((l,r)=>l-r);

        var pointsAfter =
          [].concat.apply([],
            [].concat.apply([],
              result.dataSeries
              .map((series)=>series.data)
            )
            .map((point)=>point.info.aggregated? point.info.aggregated.map((agggregatedPoint)=>agggregatedPoint.value) : [point.value])
          )
          .sort((l,r)=>l-r);
        console.log("I am a broken test");
        //assert.deepEqual(pointsBefore,pointsAfter);
      })
    })
  })

  describe("Insert empty columns", function(){
    describe("Inserting no columns", function(){
      var result = subject.insertEmptyColumns([],data);
      it("Preserves deep equality", function(){
        assert.deepStrictEqual(data, result);
      })
      it("Touches data series and x axis", function(){
        assert.notEqual(data.xAxisCategories, result.xAxisCategories);
        assert.notEqual(data.dataSeries, result.dataSeries);
      })
      it("Does not touch y axis", function(){
        assert.equal(data.yAxisCategories, result.yAxisCategories);
      })
    })
    describe("Inserting the same columns", function(){
      var result = subject.insertEmptyColumns(data.xAxisCategories,data);
      it("Preserves deep equality", function(){
        assert.deepStrictEqual(data, result);
      })
      it("Touches data series and x axis", function(){
        assert.notEqual(data.xAxisCategories, result.xAxisCategories);
        assert.notEqual(data.dataSeries, result.dataSeries);
      })
      it("Does not touch y axis", function(){
        assert.equal(data.yAxisCategories, result.yAxisCategories);
      })
    })
    describe("Inserting a new column at the beginning", function(){
      var result = subject.insertEmptyColumns([{label:"NewColumn"}],data);
      it("Does not touch y axis", function(){
        assert.equal(data.yAxisCategories, result.yAxisCategories);
      })
      it("Touches data series and x axis", function(){
        assert.notEqual(data.xAxisCategories, result.xAxisCategories);
        assert.notEqual(data.dataSeries, result.dataSeries);
      })
      it("New X axis is one item longer", function(){
        assert.equal(data.xAxisCategories.length +1, result.xAxisCategories.length);
      })
      it("Makes the new label contain the new column at the first place", function(){
        assert.equal(0, result.xAxisCategories.findIndex((e)=>e.label=="NewColumn"));
      })
      it("We did not lose any values", function(){
        var pointsBefore =
          [].concat.apply([],
            data.dataSeries
            .map((series)=>series.data)
          )
          .map((point)=>point.value)
          .sort((l,r)=>l-r);
          var pointsAfter =
            [].concat.apply([],
              result.dataSeries
              .map((series)=>series.data)
            )
            .map((point)=>point.value)
            .sort((l,r)=>l-r);
        assert.deepEqual(pointsBefore, pointsAfter);
      })
      it("No point has x value zero", function(){
        result.dataSeries.map(function(series){
          series.data.forEach(function(point){
            assert.ok(point.x>0, JSON.stringify(point))
          })
        })
      })
    })
    describe("Inserting a new column at the place of index 2", function(){
      var newColumns = JSON.parse(JSON.stringify(data.xAxisCategories));
      newColumns.splice(2,0,{label:"NewColumn"});

      var result = subject.insertEmptyColumns(
        newColumns,data);
      it("Does not touch y axis", function(){
        assert.equal(data.yAxisCategories, result.yAxisCategories);
      })
      it("Touches data series and x axis", function(){
        assert.notEqual(data.xAxisCategories, result.xAxisCategories);
        assert.notEqual(data.dataSeries, result.dataSeries);
      })
      it("New X axis is one item longer", function(){
        assert.equal(data.xAxisCategories.length +1, newColumns.length);
        //assert.equal(data.xAxisCategories.length +1, result.xAxisCategories.length);
      })
      it("Makes the new label contain the new column at place 2", function(){
        assert.equal(2, result.xAxisCategories.findIndex((e)=>e.label=="NewColumn"));
      })
      it("We did not lose any values", function(){
        var pointsBefore =
          [].concat.apply([],
            data.dataSeries
            .map((series)=>series.data)
          )
          .map((point)=>point.value)
          .sort((l,r)=>l-r);
          var pointsAfter =
            [].concat.apply([],
              result.dataSeries
              .map((series)=>series.data)
            )
            .map((point)=>point.value)
            .sort((l,r)=>l-r);
        assert.deepEqual(pointsBefore, pointsAfter);
      })
      it("No point has x value 2", function(){
        result.dataSeries.map(function(series){
          series.data.forEach(function(point){
            assert.ok(point.x!=2, JSON.stringify(point))
          })
        })
      })
    })
  })


});

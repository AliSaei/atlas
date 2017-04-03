const $ = require('jquery');
require('jquery-ui-bundle');
//TODO: make this button consistently styled, using Bootstrap or Foundation
//remove this dependency on jquery

const React = require('react');
const ReactDOM = require('react-dom');

//*------------------------------------------------------------------*

require('./DifferentialDownloadButton.css');

//*------------------------------------------------------------------*


const RequiredString = React.PropTypes.string.isRequired;
const OptionalString = React.PropTypes.string;
const RequiredNumber = React.PropTypes.number.isRequired;
const OptionalNumber = React.PropTypes.number;

const DownloadDifferentialButton = React.createClass({

    propTypes: {
        hostUrl: RequiredString,
        results: React.PropTypes.arrayOf(React.PropTypes.shape({
            species: RequiredString,
            kingdom: RequiredString,
            experimentType: RequiredString,
            numReplicates: RequiredString,    // faceting only works with strings https://issues.apache.org/jira/browse/SOLR-7496
            regulation: RequiredString,
            factors: React.PropTypes.arrayOf(OptionalString).isRequired,
            bioentityIdentifier: RequiredString,
            experimentAccession: RequiredString,
            experimentName: RequiredString,
            contrastId: RequiredString,
            comparison: RequiredString,
            foldChange: RequiredNumber,
            pValue: RequiredNumber,
            tStatistics: OptionalNumber,
            colour: RequiredString,
            id: RequiredString
        })).isRequired
    },

    _convertJsonToTsv (results) {
      const arrayResults = typeof results !== 'object' ? JSON.parse(results) : results;
      return (
       [
         ['Gene', 'Organism', 'Experiment Accession', 'Comparison', 'log2foldchange', 'pValue']
         .concat(arrayResults.some(diffResults => diffResults.tStatistics != null) ? ['tStatistics'] :[])
         .join('\t')
       ].concat(
         arrayResults.map(diffResults =>
           [
             diffResults.bioentityIdentifier,
             diffResults.species,
             diffResults.experimentAccession,
             diffResults.comparison,
             diffResults.foldChange,
             diffResults.pValue,
             diffResults.tStatistics
           ]
           .filter(el => el !== null)    // tStatistics might be missing
           .join('\t')
         )
       ).join('\n')
     )
    },

    _downloadDifferentialProfiles () {
        $(ReactDOM.findDOMNode(this._downloadProfilesLinkRef)).click();
    },

    render () {
        // let downloadImgSrcURL = this.props.hostUrl + '/gxa/resources/images/download_blue_small.png';

        let tsvString = this._convertJsonToTsv(this.props.results);
        let uri = 'data:text/tsv;charset=utf-8,' + encodeURI(tsvString);
        let fileName = 'differentialResults.tsv';

        return (
            <div style={{display: 'inline-block', verticalAlign: 'top', paddingLeft: '10px'}}>
                <a ref={c => {this._downloadProfilesLinkRef = c;}} className="button large gxaDownloadButton"
                   href={uri} download={fileName} target="_blank"
                   onClick={this._downloadDifferentialProfiles}>
                    <span className="icon icon-functional" data-icon="="></span>
                </a>
            </div>
        );
    },

    componentDidMount () {
        let $downloadProfilesLink = $(ReactDOM.findDOMNode(this._downloadProfilesLinkRef));
        $downloadProfilesLink.tooltip();
    }

});

//*------------------------------------------------------------------*

module.exports = DownloadDifferentialButton;

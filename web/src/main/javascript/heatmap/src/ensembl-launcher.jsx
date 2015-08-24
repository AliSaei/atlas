"use strict";

//*------------------------------------------------------------------*

var React = require('react');

var $ = require('jquery');
var jQuery = $;

require('jquery-ui');
require('../css/jquery-ui.min.css');

//*------------------------------------------------------------------*

var ensemblUtils = require('./ensembl-utils.js');

//*------------------------------------------------------------------*

var EnsemblLauncher = React.createClass({
    propTypes: {
        isBaseline: React.PropTypes.bool.isRequired,
        atlasHost: React.PropTypes.string.isRequired,
        contextRoot: React.PropTypes.string.isRequired,
        experimentAccession: React.PropTypes.string.isRequired,
        species: React.PropTypes.string.isRequired,
        ensemblDB: React.PropTypes.string.isRequired,
        columnType: React.PropTypes.string.isRequired,
        eventEmitter: React.PropTypes.object.isRequired
    },

    _noSelectedColumnMessageArticle: function () {
        var isVowel = (function () {
            var re = /^[aeiou]$/i;
            return function (char) {
                return re.test(char);
            }
        })();

        var beginsWithVowel = function (string) {
            return isVowel(string.charAt(0));
        };

        return beginsWithVowel(this.props.columnType) ? "an " : "a ";
    },

    _openEnsemblWindow: function (baseURL) {
        if (!this.state.selectedColumnId || !this.state.selectedGeneId) {
            return;
        }

        var ensemblSpecies = ensemblUtils.toEnsemblSpecies(this.props.species);
        var trackFileHeader = this.props.experimentAccession + "." + this.state.selectedColumnId;
        var atlasTrackBaseURL = "http://" + this.props.atlasHost + this.props.contextRoot + "/experiments/" + this.props.experimentAccession + "/tracks/";
        var contigViewBottom = "contigviewbottom=url:" + atlasTrackBaseURL + trackFileHeader + (this.props.isBaseline ? ".genes.expressions.bedGraph" : ".genes.log2foldchange.bedGraph");
        var tiling = (this.props.isBaseline || this.props.ensemblDB == "ensembl") ? "" : "=tiling,url:" + atlasTrackBaseURL + trackFileHeader + ".genes.pval.bedGraph=pvalue;";
        var ensemblTrackURL =  baseURL + ensemblSpecies + "/Location/View?g=" + this.state.selectedGeneId + ";db=core;" + contigViewBottom + tiling + ";format=BEDGRAPH";

        window.open(ensemblTrackURL, '_blank');
    },

    _onColumnSelectionChange: function (selectedColumnId) {
        this.setState({selectedColumnId: selectedColumnId});
    },

    _onGeneSelectionChange: function (selectedGeneId) {
        this.setState({selectedGeneId: selectedGeneId});
    },

    _updateButton: function() {
        var buttonEnabled = this.state.selectedColumnId && this.state.selectedGeneId ? true : false;
        $(this.refs.ensemblButton.getDOMNode()).button("option", "disabled", !buttonEnabled);
        if (this.props.ensemblDB == "plants") {
            $(this.refs.grameneButton.getDOMNode()).button("option", "disabled", !buttonEnabled);
        }
    },

    componentDidUpdate: function () {
        this._updateButton();
    },

    componentDidMount: function () {
        $(this.refs.ensemblButton.getDOMNode()).button({icons: {primary: "ui-icon-newwin"}});
        if (this.props.ensemblDB == "plants") {
            $(this.refs.grameneButton.getDOMNode()).button({icons: {primary: "ui-icon-newwin"}});
        }
        this._updateButton();
        this.props.eventEmitter.addListener('onColumnSelectionChange', this._onColumnSelectionChange);
        this.props.eventEmitter.addListener('onGeneSelectionChange', this._onGeneSelectionChange);
    },

    //componentWillUnmount: function () {
    //    this.props.eventEmitter.addListener('onColumnSelectionChange', this._onColumnSelectionChange);
    //    this.props.eventEmitter.addListener('onGeneSelectionChange', this._onGeneSelectionChange);
    //},

    getInitialState: function () {
        return {selectedColumnId: null, selectedGeneId: null, buttonText: ""};
    },

    render: function () {
        var ensemblHost = ensemblUtils.getEnsemblHost(this.props.ensemblDB);
        var grameneHost = ensemblUtils.getGrameneHost();

        return (
            <div id="ensembl-launcher-box" style={{width: "245px"}}>
                <div id="ensembl-launcher-box-ensembl">
                    <label>Ensembl Genome Browser</label>
                    <img src="/gxa/resources/images/ensembl.gif" style={{padding: "0px 5px"}}></img>
                    <button ref="ensemblButton" onClick={this._openEnsemblWindow.bind(this, ensemblHost)}>Open</button>
                </div>
                { this.props.ensemblDB == "plants" ?
                    <div id="ensembl-launcher-box-gramene" >
                        <label>Gramene Genome Browser</label>
                        <img src="/gxa/resources/images/gramene.png" style={{padding: "0px 5px"}}></img>
                        <button ref="grameneButton" onClick={this._openEnsemblWindow.bind(this, grameneHost)}>Open</button>
                    </div>
                    : null
                }
                <div style={{"fontSize": "x-small", height: "30px", padding: "9px 9px"}}>{this._helpMessage(this.state.selectedColumnId, this.state.selectedGeneId)}</div>
            </div>
        );
    },

    _helpMessage: function (selectedColumnId, selectedGeneId) {
        if (selectedColumnId && selectedGeneId) {
            return "";
        }

        var noSelectedColumnMessage = selectedColumnId ? "" : this.props.columnType;
        var noSelectedGeneMessage = selectedGeneId ? "" : "gene";

        return "Please select " + this._noSelectedColumnMessageArticle() + noSelectedColumnMessage + (!(selectedColumnId || selectedGeneId) ? " and a " : "") + noSelectedGeneMessage + " from the table";
    }
});

//*------------------------------------------------------------------*

module.exports = EnsemblLauncher;
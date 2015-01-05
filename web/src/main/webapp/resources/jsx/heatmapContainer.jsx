/** @jsx React.DOM */

/*global React */



var ExperimentDescription = (function (React) {

    return React.createClass({

        render: function () {

            var experimentURL = this.props.experiment.contextRoot + this.props.experiment.URL;

            return (
                <table width="100%">
                    <tbody>
                        <tr>
                            <td width="100%">
                                <div id="experimentDescription">
                                    <a id="goto-experiment" className="thick-link" title="Experiment Page" href={experimentURL}>{this.props.experiment.description}</a>
                                </div>
                                <div id="experimentOrganisms">Organism(s): <span style={{"font-style":"italic"}}>{this.props.experiment.allSpecies}</span></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            );
        }
    });

})(React);

var Anatomogram = (function (React) {

    return React.createClass({

        render: function () {
            function containsHuman(s) {
                return s.indexOf("human") > -1;
            }

            var height = containsHuman(this.props.anatomogram.maleAnatomogramFile) ? 360 : 250;
            var sexToggleImageSrc =this.props.anatomogram.contextRoot + this.props.anatomogram.toggleButtonImage;

            return (
                <div id="anatomogram" className="aside stickem double-click-noselection" style={{display: "inline"}}>
                    <table>
                        <tr>
                            <td style={{"padding-top": "15px", "vertical-align":"top"}}>
                                <span id="sex-toggle">
                                    <img id="sex-toggle-image" title="Switch anatomogram" className="button-image"
                                        style={{"width":"20px", "height":"38px", "padding":"2px"}}
                                        src={sexToggleImageSrc}/>
                                </span>
                            </td>
                            <td>
                                <div id="anatomogramBody" style={{"display":"inline-block", "width": "230px", "height":height}}>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div id="anatomogram-ensembl-launcher"></div>
                </div>
            );
        }
    });

})(React);

var HeatmapContainer = (function (React) {

    return React.createClass({

        render: function () {
            var Heatmap = this.props.Heatmap;
            var heatmapClass = this.props.heatmapClass ? this.props.heatmapClass : "heatmap-position" + (this.props.isWidget ? "-widget" : "");

            return (
                    <div className="block">

                        { this.props.experiment ? ExperimentDescription( {experiment: this.props.experiment} ) : null }

                        <div id="heatmap" className="row stickem-container">

                            { this.props.anatomogram ? Anatomogram( {anatomogram:this.props.anatomogram} ) : null}

                            <div id="ensembl-launcher" className="aside stickem" style={{"display":"inline"}}></div>

                            <div id="heatmap-react" className={heatmapClass}>
                                <Heatmap columnHeaders={this.props.columnHeaders} profiles={this.props.profiles} geneSetProfiles={this.props.geneSetProfiles} />
                            </div>

                            {/* TODO move into help tooltips module */}
                            <div id="help-placeholder" style={{display: "none"}}></div>

                            {/* TODO move into gene tooltips module */}
                            <div id="genenametooltip-content" style={{display: "none"}}></div>

                        </div>
                    </div>
            );
        }
    });

})(React);

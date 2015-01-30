/** @jsx React.DOM */

/*global React */
var facetsModule = (function ($, React) {

    var build = function build(facetsConfig, eventEmitter) {

        var Facets = React.createClass({displayName: 'Facets',
            propTypes: {

                /*
                 eg:
                 {
                 "homo sapiens" : [ {"factor": "CELL_LINE", "source": "Cell line"}, {"factor": "ORGANISM_PART", "source": "Tissue"}],
                 "mus musculus" : [ {"factor": "CELL_LINE", "source": "Cell line"}, {"factor": "INDIVIDUAL", "source": "Individual"}]
                 }
                 */
                facets: React.PropTypes.object,

                /*
                 eg:
                 { "homo sapiens" : { "CELL_LINE": true, "ORGANISM_PART": true } }
                 */
                checkedFacets: React.PropTypes.object
            },

            _setChecked: function (checked, species, factor) {
                this.props.setChecked(checked, species, factor);
            },

            render: function () {
                var speciesRows = Object.keys(this.props.facets).map(function (species) {
                    return Species({species: species, sources: this.props.facets[species], 
                                    checkedSources: this.props.checkedFacets && this.props.checkedFacets[species], 
                                    setChecked: this._setChecked}
                    );
                }.bind(this));

                return (
                    React.DOM.span(null, 
                        speciesRows
                    )
                );
            }
        });

        var Species = React.createClass({displayName: 'Species',
            propTypes: {
                species: React.PropTypes.string.isRequired,

                // eg: [ {"factor": "CELL_LINE", "source": "Cell line"}, {"factor": "ORGANISM_PART", "source": "Tissue"} ]
                sources: React.PropTypes.arrayOf(React.PropTypes.shape({
                    factor: React.PropTypes.string.isRequired,
                    source: React.PropTypes.string.isRequired
                })).isRequired,

                // eg: { "CELL_LINE": true, "ORGANISM_PART": true }
                checkedSources: React.PropTypes.object
            },

            _setChecked: function (checked, factor) {
                this.props.setChecked(checked, this.props.species, factor);
            },

            render: function () {
                var sourcesRows = Object.keys(this.props.sources).map(function (sources) {
                    var sourcesObject = this.props.sources[sources];
                    return Source({factor: sourcesObject.factor, 
                        name: sourcesObject.source, 
                        checked: this.props.checkedSources && this.props.checkedSources[sourcesObject.factor], 
                        setChecked: this._setChecked}
                    );

                }.bind(this));

                return (
                    React.DOM.span(null, 
                        React.DOM.div(null, 
                            React.DOM.span(null, this.props.species)
                        ), 
                    sourcesRows
                    )
                );
            }
        });

        var Source = React.createClass({displayName: 'Source',

            _setChecked: function () {
                this.props.setChecked(!this.props.checked, this.props.factor);
            },

            render: function () {
                return (
                    React.DOM.div(null, 
                        React.DOM.input({type: "checkbox", defaultChecked: this.props.checked, 
                            onChange: this._setChecked}), " ", this.props.name
                    )
                );
            }
        });

        return {
            Facets: Facets
        };

    };

    return {
        build: function (facetsConfig) {
            return build(facetsConfig, new EventEmitter());
        }
    };

})(jQuery, React, EventEmitter);
/** @jsx React.DOM */

/*global React */
var Heatmaps = (function (React) {

    return React.createClass({

        render: function () {
            // this.props.geneQuery
            return (
                <div>
                    {JSON.stringify(this.props.heatmaps)}
                </div>
            );
        }
    });

})(React);

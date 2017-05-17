var expressionAtlasBioentityInformation =
webpackJsonp_name_([3],{

/***/ 0:
/*!******************************************************!*\
  !*** ./atlas_bundles/bioentity-information/index.js ***!
  \******************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	module.exports = __webpack_require__(/*! ./src/renderer.js */ 3214);

/***/ },

/***/ 3214:
/*!*************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/src/renderer.js ***!
  \*************************************************************/
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	var React = __webpack_require__(/*! react */ 300);
	var ReactDOM = __webpack_require__(/*! react-dom */ 332);
	
	var BioentityInformation = __webpack_require__(/*! ./BioentityInformation.jsx */ 3215);
	
	exports.render = function (options) {
	    ReactDOM.render(React.createElement(BioentityInformation, { bioentityProperties: options.payload }), typeof options.target === "string" ? document.getElementById(options.target) : options.target);
	};

/***/ },

/***/ 3215:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/src/BioentityInformation.jsx ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	"use strict";
	
	var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };
	
	var React = __webpack_require__(/*! react */ 300);
	__webpack_require__(/*! ./BioentityInformation.css */ 3216);
	
	var PropertyLinkShape = {
	  text: React.PropTypes.string.isRequired,
	  url: React.PropTypes.string.isRequired,
	  relevance: React.PropTypes.number.isRequired
	};
	
	var BioentityPropertyShape = {
	  type: React.PropTypes.string.isRequired,
	  name: React.PropTypes.string.isRequired,
	  values: React.PropTypes.arrayOf(React.PropTypes.shape(PropertyLinkShape)).isRequired
	};
	
	var BioentityPropertiesShape = {
	  bioentityProperties: React.PropTypes.arrayOf(React.PropTypes.shape(BioentityPropertyShape))
	};
	
	var BioentityProperty = React.createClass({
	  displayName: "BioentityProperty",
	
	  propTypes: BioentityPropertyShape,
	
	  getInitialState: function getInitialState() {
	    return {
	      showAll: false
	    };
	  },
	
	
	  // take three most relevant links and then all of the same relevance
	  _pickMostRelevant: function _pickMostRelevant(properties) {
	    var relevanceThreshold = properties.map(function (p) {
	      return p.relevance;
	    }).sort(function (l, r) {
	      return r - l;
	    }).concat([0, 0, 0])[properties.size < 3 ? properties.size - 1 : 2];
	    return properties.filter(function (p) {
	      return p.relevance >= relevanceThreshold;
	    });
	  },
	  _renderProperty: function _renderProperty(property, ix) {
	    return property.url ? React.createElement(
	      "a",
	      { key: property.url + " " + ix, className: "bioEntityCardLink", href: property.url, target: "_blank" },
	      property.text
	    ) : React.createElement(
	      "span",
	      { key: property.text + " " + ix },
	      property.text
	    );
	  },
	  _zipWithCommaSpans: function _zipWithCommaSpans(elts) {
	    return [].concat.apply([], elts.map(function (e, ix) {
	      return [e, React.createElement(
	        "span",
	        { key: "comma " + ix },
	        ", "
	      )];
	    })).slice(0, -1);
	  },
	  render: function render() {
	    var numUnshownLinks = this.props.values.length - this._pickMostRelevant(this.props.values).length;
	    var hasOptionalLinks = ["go", "po"].indexOf(this.props.type) > -1 && numUnshownLinks > 0;
	
	    return React.createElement(
	      "tr",
	      null,
	      React.createElement(
	        "td",
	        { className: "gxaBioentityInformationCardPropertyType" },
	        this.props.name
	      ),
	      React.createElement(
	        "td",
	        null,
	        React.createElement(
	          "div",
	          null,
	          hasOptionalLinks ? React.createElement(
	            "span",
	            null,
	            this._zipWithCommaSpans((this.state.showAll ? this.props.values : this._pickMostRelevant(this.props.values)).sort(function (l, r) {
	              return r.relevance === l.relevance ? r.text.toLowerCase() < l.text.toLowerCase() ? 1 : -1 : r.relevance - l.relevance;
	            }).map(this._renderProperty)),
	            React.createElement(
	              "a",
	              { role: "button", style: { cursor: "pointer" },
	                onClick: function () {
	                  this.setState(function (previousState) {
	                    return { showAll: !previousState.showAll };
	                  });
	                }.bind(this) },
	              this.state.showAll ? " (show less)" : " … and " + numUnshownLinks + " more"
	            )
	          ) : this._zipWithCommaSpans(this.props.values.map(this._renderProperty))
	        )
	      )
	    );
	  }
	});
	
	var BioentityInformation = React.createClass({
	  displayName: "BioentityInformation",
	
	  propTypes: BioentityPropertiesShape,
	
	  render: function render() {
	    return React.createElement(
	      "div",
	      { className: "gxaBioentityInformationCard" },
	      React.createElement(
	        "table",
	        null,
	        React.createElement(
	          "tbody",
	          null,
	          this.props.bioentityProperties.map(function (bioentityProperty) {
	            return React.createElement(BioentityProperty, _extends({
	              key: bioentityProperty.type
	            }, bioentityProperty));
	          })
	        )
	      )
	    );
	  }
	});
	
	module.exports = BioentityInformation;

/***/ },

/***/ 3216:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/src/BioentityInformation.css ***!
  \**************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(/*! !./../~/css-loader!./BioentityInformation.css */ 3217);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(/*! ./../~/style-loader/addStyles.js */ 3219)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./BioentityInformation.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./BioentityInformation.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },

/***/ 3217:
/*!*****************************************************************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/~/css-loader!./atlas_bundles/bioentity-information/src/BioentityInformation.css ***!
  \*****************************************************************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(/*! ./../~/css-loader/lib/css-base.js */ 3218)();
	// imports
	
	
	// module
	exports.push([module.id, ".gxaBioentityInformationCard {\n    margin-top: 20px;\n}\n\n.gxaBioentityInformationCard table {\n    margin: 0;\n    width: auto;\n    border: none;\n}\n\n.gxaBioentityInformationCard td {\n    border: none;\n}\n\n\ntd.gxaBioentityInformationCardPropertyType {\n    font-size: 14px;\n    font-weight: bold;\n    white-space: nowrap;\n    padding-right: 2em;\n    border:none;\n}\n\n.gxaBioentityInformationCardPropertyValue {\n    border: none;\n}\n", ""]);
	
	// exports


/***/ },

/***/ 3218:
/*!**************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/~/css-loader/lib/css-base.js ***!
  \**************************************************************************/
787,

/***/ 3219:
/*!*************************************************************************!*\
  !*** ./atlas_bundles/bioentity-information/~/style-loader/addStyles.js ***!
  \*************************************************************************/
/***/ function(module, exports, __webpack_require__) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	var stylesInDom = {},
		memoize = function(fn) {
			var memo;
			return function () {
				if (typeof memo === "undefined") memo = fn.apply(this, arguments);
				return memo;
			};
		},
		isOldIE = memoize(function() {
			return /msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase());
		}),
		getHeadElement = memoize(function () {
			return document.head || document.getElementsByTagName("head")[0];
		}),
		singletonElement = null,
		singletonCounter = 0,
		styleElementsInsertedAtTop = [];
	
	module.exports = function(list, options) {
		if(true) {
			if(typeof document !== "object") throw new Error("The style-loader cannot be used in a non-browser environment");
		}
	
		options = options || {};
		// Force single-tag solution on IE6-9, which has a hard limit on the # of <style>
		// tags it will allow on a page
		if (typeof options.singleton === "undefined") options.singleton = isOldIE();
	
		// By default, add <style> tags to the bottom of <head>.
		if (typeof options.insertAt === "undefined") options.insertAt = "bottom";
	
		var styles = listToStyles(list);
		addStylesToDom(styles, options);
	
		return function update(newList) {
			var mayRemove = [];
			for(var i = 0; i < styles.length; i++) {
				var item = styles[i];
				var domStyle = stylesInDom[item.id];
				domStyle.refs--;
				mayRemove.push(domStyle);
			}
			if(newList) {
				var newStyles = listToStyles(newList);
				addStylesToDom(newStyles, options);
			}
			for(var i = 0; i < mayRemove.length; i++) {
				var domStyle = mayRemove[i];
				if(domStyle.refs === 0) {
					for(var j = 0; j < domStyle.parts.length; j++)
						domStyle.parts[j]();
					delete stylesInDom[domStyle.id];
				}
			}
		};
	}
	
	function addStylesToDom(styles, options) {
		for(var i = 0; i < styles.length; i++) {
			var item = styles[i];
			var domStyle = stylesInDom[item.id];
			if(domStyle) {
				domStyle.refs++;
				for(var j = 0; j < domStyle.parts.length; j++) {
					domStyle.parts[j](item.parts[j]);
				}
				for(; j < item.parts.length; j++) {
					domStyle.parts.push(addStyle(item.parts[j], options));
				}
			} else {
				var parts = [];
				for(var j = 0; j < item.parts.length; j++) {
					parts.push(addStyle(item.parts[j], options));
				}
				stylesInDom[item.id] = {id: item.id, refs: 1, parts: parts};
			}
		}
	}
	
	function listToStyles(list) {
		var styles = [];
		var newStyles = {};
		for(var i = 0; i < list.length; i++) {
			var item = list[i];
			var id = item[0];
			var css = item[1];
			var media = item[2];
			var sourceMap = item[3];
			var part = {css: css, media: media, sourceMap: sourceMap};
			if(!newStyles[id])
				styles.push(newStyles[id] = {id: id, parts: [part]});
			else
				newStyles[id].parts.push(part);
		}
		return styles;
	}
	
	function insertStyleElement(options, styleElement) {
		var head = getHeadElement();
		var lastStyleElementInsertedAtTop = styleElementsInsertedAtTop[styleElementsInsertedAtTop.length - 1];
		if (options.insertAt === "top") {
			if(!lastStyleElementInsertedAtTop) {
				head.insertBefore(styleElement, head.firstChild);
			} else if(lastStyleElementInsertedAtTop.nextSibling) {
				head.insertBefore(styleElement, lastStyleElementInsertedAtTop.nextSibling);
			} else {
				head.appendChild(styleElement);
			}
			styleElementsInsertedAtTop.push(styleElement);
		} else if (options.insertAt === "bottom") {
			head.appendChild(styleElement);
		} else {
			throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");
		}
	}
	
	function removeStyleElement(styleElement) {
		styleElement.parentNode.removeChild(styleElement);
		var idx = styleElementsInsertedAtTop.indexOf(styleElement);
		if(idx >= 0) {
			styleElementsInsertedAtTop.splice(idx, 1);
		}
	}
	
	function createStyleElement(options) {
		var styleElement = document.createElement("style");
		styleElement.type = "text/css";
		insertStyleElement(options, styleElement);
		return styleElement;
	}
	
	function createLinkElement(options) {
		var linkElement = document.createElement("link");
		linkElement.rel = "stylesheet";
		insertStyleElement(options, linkElement);
		return linkElement;
	}
	
	function addStyle(obj, options) {
		var styleElement, update, remove;
	
		if (options.singleton) {
			var styleIndex = singletonCounter++;
			styleElement = singletonElement || (singletonElement = createStyleElement(options));
			update = applyToSingletonTag.bind(null, styleElement, styleIndex, false);
			remove = applyToSingletonTag.bind(null, styleElement, styleIndex, true);
		} else if(obj.sourceMap &&
			typeof URL === "function" &&
			typeof URL.createObjectURL === "function" &&
			typeof URL.revokeObjectURL === "function" &&
			typeof Blob === "function" &&
			typeof btoa === "function") {
			styleElement = createLinkElement(options);
			update = updateLink.bind(null, styleElement);
			remove = function() {
				removeStyleElement(styleElement);
				if(styleElement.href)
					URL.revokeObjectURL(styleElement.href);
			};
		} else {
			styleElement = createStyleElement(options);
			update = applyToTag.bind(null, styleElement);
			remove = function() {
				removeStyleElement(styleElement);
			};
		}
	
		update(obj);
	
		return function updateStyle(newObj) {
			if(newObj) {
				if(newObj.css === obj.css && newObj.media === obj.media && newObj.sourceMap === obj.sourceMap)
					return;
				update(obj = newObj);
			} else {
				remove();
			}
		};
	}
	
	var replaceText = (function () {
		var textStore = [];
	
		return function (index, replacement) {
			textStore[index] = replacement;
			return textStore.filter(Boolean).join('\n');
		};
	})();
	
	function applyToSingletonTag(styleElement, index, remove, obj) {
		var css = remove ? "" : obj.css;
	
		if (styleElement.styleSheet) {
			styleElement.styleSheet.cssText = replaceText(index, css);
		} else {
			var cssNode = document.createTextNode(css);
			var childNodes = styleElement.childNodes;
			if (childNodes[index]) styleElement.removeChild(childNodes[index]);
			if (childNodes.length) {
				styleElement.insertBefore(cssNode, childNodes[index]);
			} else {
				styleElement.appendChild(cssNode);
			}
		}
	}
	
	function applyToTag(styleElement, obj) {
		var css = obj.css;
		var media = obj.media;
	
		if(media) {
			styleElement.setAttribute("media", media)
		}
	
		if(styleElement.styleSheet) {
			styleElement.styleSheet.cssText = css;
		} else {
			while(styleElement.firstChild) {
				styleElement.removeChild(styleElement.firstChild);
			}
			styleElement.appendChild(document.createTextNode(css));
		}
	}
	
	function updateLink(linkElement, obj) {
		var css = obj.css;
		var sourceMap = obj.sourceMap;
	
		if(sourceMap) {
			// http://stackoverflow.com/a/26603875
			css += "\n/*# sourceMappingURL=data:application/json;base64," + btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap)))) + " */";
		}
	
		var blob = new Blob([css], { type: "text/css" });
	
		var oldSrc = linkElement.href;
	
		linkElement.href = URL.createObjectURL(blob);
	
		if(oldSrc)
			URL.revokeObjectURL(oldSrc);
	}


/***/ }

});
//# sourceMappingURL=expressionAtlasBioentityInformation.bundle.js.map
var internalAtlasHeatmap =
webpackJsonp_name_([5],[
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);

	var $ = __webpack_require__(747);

	//*------------------------------------------------------------------*

	var HeatmapAnatomogramContainer = __webpack_require__(1134);

	var ExperimentTypes = __webpack_require__(858);

	//*------------------------------------------------------------------*

	/**
	 * @param {Object} options
	 * @param {Object} options.heatmapData
	 * @param {boolean} options.isMultiExperiment
	 * @param {boolean} options.isDifferential
	 * @param {boolean} options.isProteomicsBaseline
	 */
	function drawHeatmap (options) {

	    var heatmapConfig = options.heatmapData.config,
	        columnHeaders = options.heatmapData.columnHeaders,
	        nonExpressedColumnHeaders = options.heatmapData.nonExpressedColumnHeaders,
	        multipleColumnHeaders = options.heatmapData.multipleColumnHeaders,
	        profiles = options.heatmapData.profiles,
	        geneSetProfiles = options.heatmapData.geneSetProfiles,
	        anatomogramData = options.heatmapData.anatomogram;

	    var type =
	        options.isMultiExperiment ? ExperimentTypes.MULTIEXPERIMENT :
	            options.isDifferential ? ExperimentTypes.DIFFERENTIAL :
	                options.isProteomicsBaseline ? ExperimentTypes.PROTEOMICS_BASELINE :
	                    ExperimentTypes.BASELINE;

	    ReactDOM.render(
	        React.createElement(
	            HeatmapAnatomogramContainer,
	            {
	                type: type, heatmapConfig: heatmapConfig, isWidget: false,
	                anatomogram: anatomogramData, columnHeaders: columnHeaders, nonExpressedColumnHeaders: nonExpressedColumnHeaders,
	                multipleColumnHeaders: multipleColumnHeaders,
	                profiles: profiles, geneSetProfiles: geneSetProfiles,
	                atlasBaseURL: heatmapConfig.atlasHost + heatmapConfig.contextRoot, linksAtlasBaseURL: heatmapConfig.atlasHost + heatmapConfig.contextRoot
	            }
	        ),
	        document.getElementById("gxaExperimentPageHeatmapAnatomogram")
	    );

	}

	//*------------------------------------------------------------------*

	module.exports = drawHeatmap;

/***/ },
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */,
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */,
/* 18 */,
/* 19 */,
/* 20 */,
/* 21 */,
/* 22 */,
/* 23 */,
/* 24 */,
/* 25 */,
/* 26 */,
/* 27 */,
/* 28 */,
/* 29 */,
/* 30 */,
/* 31 */,
/* 32 */,
/* 33 */,
/* 34 */,
/* 35 */,
/* 36 */,
/* 37 */,
/* 38 */,
/* 39 */,
/* 40 */,
/* 41 */,
/* 42 */,
/* 43 */,
/* 44 */,
/* 45 */,
/* 46 */,
/* 47 */,
/* 48 */,
/* 49 */,
/* 50 */,
/* 51 */,
/* 52 */,
/* 53 */,
/* 54 */,
/* 55 */,
/* 56 */,
/* 57 */,
/* 58 */,
/* 59 */,
/* 60 */,
/* 61 */,
/* 62 */,
/* 63 */,
/* 64 */,
/* 65 */,
/* 66 */,
/* 67 */,
/* 68 */,
/* 69 */,
/* 70 */,
/* 71 */,
/* 72 */,
/* 73 */,
/* 74 */,
/* 75 */,
/* 76 */,
/* 77 */,
/* 78 */,
/* 79 */,
/* 80 */,
/* 81 */,
/* 82 */,
/* 83 */,
/* 84 */,
/* 85 */,
/* 86 */,
/* 87 */,
/* 88 */,
/* 89 */,
/* 90 */,
/* 91 */,
/* 92 */,
/* 93 */,
/* 94 */,
/* 95 */,
/* 96 */,
/* 97 */,
/* 98 */,
/* 99 */,
/* 100 */,
/* 101 */,
/* 102 */,
/* 103 */,
/* 104 */,
/* 105 */,
/* 106 */,
/* 107 */,
/* 108 */,
/* 109 */,
/* 110 */,
/* 111 */,
/* 112 */,
/* 113 */,
/* 114 */,
/* 115 */,
/* 116 */,
/* 117 */,
/* 118 */,
/* 119 */,
/* 120 */,
/* 121 */,
/* 122 */,
/* 123 */,
/* 124 */,
/* 125 */,
/* 126 */,
/* 127 */,
/* 128 */,
/* 129 */,
/* 130 */,
/* 131 */,
/* 132 */,
/* 133 */,
/* 134 */,
/* 135 */,
/* 136 */,
/* 137 */,
/* 138 */,
/* 139 */,
/* 140 */,
/* 141 */,
/* 142 */,
/* 143 */,
/* 144 */,
/* 145 */,
/* 146 */,
/* 147 */,
/* 148 */,
/* 149 */,
/* 150 */,
/* 151 */,
/* 152 */,
/* 153 */,
/* 154 */,
/* 155 */,
/* 156 */,
/* 157 */,
/* 158 */,
/* 159 */,
/* 160 */,
/* 161 */,
/* 162 */,
/* 163 */,
/* 164 */,
/* 165 */,
/* 166 */,
/* 167 */,
/* 168 */,
/* 169 */,
/* 170 */,
/* 171 */,
/* 172 */,
/* 173 */,
/* 174 */,
/* 175 */,
/* 176 */,
/* 177 */,
/* 178 */,
/* 179 */,
/* 180 */,
/* 181 */,
/* 182 */,
/* 183 */,
/* 184 */,
/* 185 */,
/* 186 */,
/* 187 */,
/* 188 */,
/* 189 */,
/* 190 */,
/* 191 */,
/* 192 */,
/* 193 */,
/* 194 */,
/* 195 */,
/* 196 */,
/* 197 */,
/* 198 */,
/* 199 */,
/* 200 */,
/* 201 */,
/* 202 */,
/* 203 */,
/* 204 */,
/* 205 */,
/* 206 */,
/* 207 */,
/* 208 */,
/* 209 */,
/* 210 */,
/* 211 */,
/* 212 */,
/* 213 */,
/* 214 */,
/* 215 */,
/* 216 */,
/* 217 */,
/* 218 */,
/* 219 */,
/* 220 */,
/* 221 */,
/* 222 */,
/* 223 */,
/* 224 */,
/* 225 */,
/* 226 */,
/* 227 */,
/* 228 */,
/* 229 */,
/* 230 */,
/* 231 */,
/* 232 */,
/* 233 */,
/* 234 */,
/* 235 */,
/* 236 */,
/* 237 */,
/* 238 */,
/* 239 */,
/* 240 */,
/* 241 */,
/* 242 */,
/* 243 */,
/* 244 */,
/* 245 */,
/* 246 */,
/* 247 */,
/* 248 */,
/* 249 */,
/* 250 */,
/* 251 */,
/* 252 */,
/* 253 */,
/* 254 */,
/* 255 */,
/* 256 */,
/* 257 */,
/* 258 */,
/* 259 */,
/* 260 */,
/* 261 */,
/* 262 */,
/* 263 */,
/* 264 */,
/* 265 */,
/* 266 */,
/* 267 */,
/* 268 */,
/* 269 */,
/* 270 */,
/* 271 */,
/* 272 */,
/* 273 */,
/* 274 */,
/* 275 */,
/* 276 */,
/* 277 */,
/* 278 */,
/* 279 */,
/* 280 */,
/* 281 */,
/* 282 */,
/* 283 */,
/* 284 */,
/* 285 */,
/* 286 */,
/* 287 */,
/* 288 */,
/* 289 */,
/* 290 */,
/* 291 */,
/* 292 */,
/* 293 */,
/* 294 */,
/* 295 */,
/* 296 */,
/* 297 */,
/* 298 */,
/* 299 */,
/* 300 */,
/* 301 */,
/* 302 */,
/* 303 */,
/* 304 */,
/* 305 */,
/* 306 */,
/* 307 */,
/* 308 */,
/* 309 */,
/* 310 */,
/* 311 */,
/* 312 */,
/* 313 */,
/* 314 */,
/* 315 */,
/* 316 */,
/* 317 */,
/* 318 */,
/* 319 */,
/* 320 */,
/* 321 */,
/* 322 */,
/* 323 */,
/* 324 */,
/* 325 */,
/* 326 */,
/* 327 */,
/* 328 */,
/* 329 */,
/* 330 */,
/* 331 */,
/* 332 */,
/* 333 */,
/* 334 */,
/* 335 */,
/* 336 */,
/* 337 */,
/* 338 */,
/* 339 */,
/* 340 */,
/* 341 */,
/* 342 */,
/* 343 */,
/* 344 */,
/* 345 */,
/* 346 */,
/* 347 */,
/* 348 */,
/* 349 */,
/* 350 */,
/* 351 */,
/* 352 */,
/* 353 */,
/* 354 */,
/* 355 */,
/* 356 */,
/* 357 */,
/* 358 */,
/* 359 */,
/* 360 */,
/* 361 */,
/* 362 */,
/* 363 */,
/* 364 */,
/* 365 */,
/* 366 */,
/* 367 */,
/* 368 */,
/* 369 */,
/* 370 */,
/* 371 */,
/* 372 */,
/* 373 */,
/* 374 */,
/* 375 */,
/* 376 */,
/* 377 */,
/* 378 */,
/* 379 */,
/* 380 */,
/* 381 */,
/* 382 */,
/* 383 */,
/* 384 */,
/* 385 */,
/* 386 */,
/* 387 */,
/* 388 */,
/* 389 */,
/* 390 */,
/* 391 */,
/* 392 */,
/* 393 */,
/* 394 */,
/* 395 */,
/* 396 */,
/* 397 */,
/* 398 */,
/* 399 */,
/* 400 */,
/* 401 */,
/* 402 */,
/* 403 */,
/* 404 */,
/* 405 */,
/* 406 */,
/* 407 */,
/* 408 */,
/* 409 */,
/* 410 */,
/* 411 */,
/* 412 */,
/* 413 */,
/* 414 */,
/* 415 */,
/* 416 */,
/* 417 */,
/* 418 */,
/* 419 */,
/* 420 */,
/* 421 */,
/* 422 */,
/* 423 */,
/* 424 */,
/* 425 */,
/* 426 */,
/* 427 */,
/* 428 */,
/* 429 */,
/* 430 */,
/* 431 */,
/* 432 */,
/* 433 */,
/* 434 */,
/* 435 */,
/* 436 */,
/* 437 */,
/* 438 */,
/* 439 */,
/* 440 */,
/* 441 */,
/* 442 */,
/* 443 */,
/* 444 */,
/* 445 */,
/* 446 */,
/* 447 */,
/* 448 */,
/* 449 */,
/* 450 */,
/* 451 */,
/* 452 */,
/* 453 */,
/* 454 */,
/* 455 */,
/* 456 */,
/* 457 */,
/* 458 */,
/* 459 */,
/* 460 */,
/* 461 */,
/* 462 */,
/* 463 */,
/* 464 */,
/* 465 */,
/* 466 */,
/* 467 */,
/* 468 */,
/* 469 */,
/* 470 */,
/* 471 */,
/* 472 */,
/* 473 */,
/* 474 */,
/* 475 */,
/* 476 */,
/* 477 */,
/* 478 */,
/* 479 */,
/* 480 */,
/* 481 */,
/* 482 */,
/* 483 */,
/* 484 */,
/* 485 */,
/* 486 */,
/* 487 */,
/* 488 */,
/* 489 */,
/* 490 */,
/* 491 */,
/* 492 */,
/* 493 */,
/* 494 */,
/* 495 */,
/* 496 */,
/* 497 */,
/* 498 */,
/* 499 */,
/* 500 */,
/* 501 */,
/* 502 */,
/* 503 */,
/* 504 */,
/* 505 */,
/* 506 */,
/* 507 */,
/* 508 */,
/* 509 */,
/* 510 */,
/* 511 */,
/* 512 */,
/* 513 */,
/* 514 */,
/* 515 */,
/* 516 */,
/* 517 */,
/* 518 */,
/* 519 */,
/* 520 */,
/* 521 */,
/* 522 */,
/* 523 */,
/* 524 */,
/* 525 */,
/* 526 */,
/* 527 */,
/* 528 */,
/* 529 */,
/* 530 */,
/* 531 */,
/* 532 */,
/* 533 */,
/* 534 */,
/* 535 */,
/* 536 */,
/* 537 */,
/* 538 */,
/* 539 */,
/* 540 */,
/* 541 */,
/* 542 */,
/* 543 */,
/* 544 */,
/* 545 */,
/* 546 */,
/* 547 */,
/* 548 */,
/* 549 */,
/* 550 */,
/* 551 */,
/* 552 */,
/* 553 */,
/* 554 */,
/* 555 */,
/* 556 */,
/* 557 */,
/* 558 */,
/* 559 */,
/* 560 */,
/* 561 */,
/* 562 */,
/* 563 */,
/* 564 */,
/* 565 */,
/* 566 */,
/* 567 */,
/* 568 */,
/* 569 */,
/* 570 */,
/* 571 */,
/* 572 */,
/* 573 */,
/* 574 */,
/* 575 */,
/* 576 */,
/* 577 */,
/* 578 */,
/* 579 */,
/* 580 */,
/* 581 */,
/* 582 */,
/* 583 */,
/* 584 */,
/* 585 */,
/* 586 */,
/* 587 */,
/* 588 */,
/* 589 */
[1145, 590],
/* 590 */
[1146, 591, 735, 739, 626, 744],
/* 591 */
[1147, 592, 593, 658, 632, 615, 605, 637, 641, 733, 678, 734, 612, 596],
/* 592 */
170,
/* 593 */
[1148, 594, 609, 613, 615, 626, 608, 607, 657],
/* 594 */
[1149, 595, 603, 605, 606, 607, 600],
/* 595 */
[1150, 596, 597, 602, 601, 600],
/* 596 */
174,
/* 597 */
[1151, 596, 598, 601, 600],
/* 598 */
[1152, 599],
/* 599 */
[1153, 600],
/* 600 */
178,
/* 601 */
[1154, 596, 600],
/* 602 */
180,
/* 603 */
[1155, 604],
/* 604 */
[1156, 600],
/* 605 */
183,
/* 606 */
[1157, 596],
/* 607 */
[1158, 596, 608, 606],
/* 608 */
186,
/* 609 */
[1159, 610, 605, 611, 612],
/* 610 */
[1160, 600],
/* 611 */
[1161, 608],
/* 612 */
[1162, 602],
/* 613 */
[1163, 614, 615],
/* 614 */
[1164, 594, 609, 615, 605, 600],
/* 615 */
[1165, 610, 616, 592, 628, 629, 631, 632, 634, 635, 605, 637, 640, 641, 626, 645, 646, 649, 600, 606, 654, 657, 612],
/* 616 */
[1166, 617, 618, 619, 624, 605, 625, 626, 627],
/* 617 */
[1167, 604],
/* 618 */
[1168, 619, 620, 621, 622, 623, 600, 612],
/* 619 */
[1169, 600],
/* 620 */
[1170, 617, 621, 600, 612],
/* 621 */
199,
/* 622 */
[1171, 600],
/* 623 */
201,
/* 624 */
[1172, 618],
/* 625 */
203,
/* 626 */
204,
/* 627 */
[1173, 596],
/* 628 */
206,
/* 629 */
[1174, 592, 626, 630],
/* 630 */
208,
/* 631 */
209,
/* 632 */
[1175, 633, 600],
/* 633 */
211,
/* 634 */
212,
/* 635 */
[1176, 636],
/* 636 */
214,
/* 637 */
[1177, 638],
/* 638 */
[1178, 639],
/* 639 */
[1179, 600],
/* 640 */
[1180, 592, 629, 634, 641, 626, 600, 612],
/* 641 */
[1181, 642, 643, 605, 637, 644, 626, 600],
/* 642 */
[1182, 643, 626, 600],
/* 643 */
[1183, 600],
/* 644 */
[1184, 600],
/* 645 */
223,
/* 646 */
[1185, 647],
/* 647 */
[1186, 648],
/* 648 */
226,
/* 649 */
[1187, 650, 655, 656, 626, 600, 612],
/* 650 */
[1188, 651, 592, 629, 634, 605, 652, 653, 637, 640, 626, 645, 600, 654, 612],
/* 651 */
[1189, 600],
/* 652 */
[1190, 604],
/* 653 */
231,
/* 654 */
232,
/* 655 */
[1191, 629, 631, 637, 626],
/* 656 */
[1192, 626, 600],
/* 657 */
[1193, 626, 602, 612],
/* 658 */
[1194, 659, 667, 670, 671, 672, 596, 676, 677, 613, 679, 680, 593, 705, 708, 632, 615, 712, 717, 718, 719, 728, 729],
/* 659 */
[1195, 617, 660, 596, 661, 663, 665, 666],
/* 660 */
[1196, 617, 618, 612, 622, 623],
/* 661 */
[1197, 643, 626, 662],
/* 662 */
[1198, 596],
/* 663 */
[1199, 664],
/* 664 */
[1200, 643, 626, 602, 612],
/* 665 */
[1201, 664],
/* 666 */
244,
/* 667 */
[1202, 617, 618, 660, 596, 641, 664, 668, 627, 669, 666],
/* 668 */
246,
/* 669 */
247,
/* 670 */
248,
/* 671 */
[1203, 666],
/* 672 */
[1204, 617, 660, 673, 615, 666],
/* 673 */
[1205, 674, 625, 675],
/* 674 */
[1206, 664, 668],
/* 675 */
253,
/* 676 */
[1207, 610, 596],
/* 677 */
[1208, 634, 678, 612],
/* 678 */
[1209, 592, 634, 615, 600, 612],
/* 679 */
[1210, 641, 644, 626, 602],
/* 680 */
[1211, 681, 683, 610, 609, 617, 616, 613, 691, 692, 696, 699, 700, 615, 701, 605, 640, 626, 630, 608, 600, 627, 666, 606, 607, 704, 657, 612],
/* 681 */
[1212, 615, 678, 682],
/* 682 */
260,
/* 683 */
[1213, 684, 596, 605, 685, 687, 688, 690, 612],
/* 684 */
262,
/* 685 */
[1214, 686],
/* 686 */
264,
/* 687 */
[1215, 684],
/* 688 */
[1216, 689],
/* 689 */
267,
/* 690 */
268,
/* 691 */
269,
/* 692 */
[1217, 614, 693, 615, 641, 626, 600],
/* 693 */
[1218, 694, 652, 600, 612],
/* 694 */
[1219, 629, 653, 602, 695],
/* 695 */
273,
/* 696 */
[1220, 697, 699, 626, 612],
/* 697 */
[1221, 643, 629, 602, 698],
/* 698 */
[1222, 592, 629, 632, 695, 600, 612],
/* 699 */
[1223, 693, 615, 641, 626, 612],
/* 700 */
[1224, 693, 614, 641, 626, 600, 612],
/* 701 */
[1225, 651, 603, 592, 637, 702, 703],
/* 702 */
[1226, 637, 649, 654, 698, 612],
/* 703 */
[1227, 698, 612],
/* 704 */
282,
/* 705 */
[1228, 706, 596, 643, 632, 615, 641, 626, 668, 707],
/* 706 */
[1229, 602],
/* 707 */
285,
/* 708 */
[1230, 610, 618, 651, 709, 655, 616, 656, 605, 633, 641],
/* 709 */
[1231, 710, 629, 652, 653, 711, 626, 645, 600, 604, 666, 612],
/* 710 */
[1232, 711, 630, 645, 600, 612],
/* 711 */
[1233, 612],
/* 712 */
[1234, 642, 643, 616, 628, 713, 644, 626],
/* 713 */
[1235, 714, 646, 682, 716],
/* 714 */
[1236, 596, 715, 662],
/* 715 */
293,
/* 716 */
294,
/* 717 */
[1237, 617, 660, 596, 713, 664, 716, 669, 666, 704],
/* 718 */
296,
/* 719 */
[1238, 617, 706, 660, 615, 720, 664, 721, 722, 673, 725, 726, 674, 727, 602, 723, 600, 666],
/* 720 */
[1239, 664],
/* 721 */
[1240, 674],
/* 722 */
[1241, 674, 723, 724, 675],
/* 723 */
301,
/* 724 */
[1242, 723],
/* 725 */
[1243, 673],
/* 726 */
[1244, 674, 675],
/* 727 */
[1245, 673],
/* 728 */
[1246, 610],
/* 729 */
[1247, 610, 730, 615, 605, 731],
/* 730 */
[1248, 626],
/* 731 */
[1249, 732],
/* 732 */
[1250, 596],
/* 733 */
311,
/* 734 */
[1251, 615],
/* 735 */
[1252, 658, 736, 733],
/* 736 */
[1253, 679, 629, 632, 635, 737, 738, 641, 645, 649, 600],
/* 737 */
315,
/* 738 */
[1254, 643, 642, 644, 626, 602],
/* 739 */
[1255, 697, 710, 709, 740, 629, 741, 694, 733, 626, 743],
/* 740 */
[1256, 629, 741, 742],
/* 741 */
[1257, 629, 652, 653, 592, 630, 695, 600, 612],
/* 742 */
320,
/* 743 */
[1258, 629, 600],
/* 744 */
[1259, 626, 612],
/* 745 */
[1260, 591],
/* 746 */,
/* 747 */
569,
/* 748 */,
/* 749 */
[1284, 747],
/* 750 */,
/* 751 */,
/* 752 */,
/* 753 */,
/* 754 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(755);

/***/ },
/* 755 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);

	var $ = __webpack_require__(747);
	__webpack_require__(756);
	__webpack_require__(757);

	var Snap = __webpack_require__(758);

	var EventEmitter = __webpack_require__(160);

	//*------------------------------------------------------------------*

	var AnatomogramSelectImageButton = React.createClass({
	    displayName: 'AnatomogramSelectImageButton',

	    propTypes: {
	        anatomogramId: React.PropTypes.string.isRequired,
	        selected: React.PropTypes.bool.isRequired,
	        toggleSrcTemplate: React.PropTypes.string.isRequired,
	        onClick: React.PropTypes.func.isRequired
	    },

	    render: function () {
	        var selectedToggleSrc = this.props.toggleSrcTemplate + "_selected.png",
	            unselectedToggleSrc = this.props.toggleSrcTemplate + "_unselected.png";

	        return React.createElement(
	            'div',
	            null,
	            React.createElement('img', { ref: 'toggleButton', onClick: this._onClick, src: this.props.selected ? selectedToggleSrc : unselectedToggleSrc,
	                style: { width: "20px", height: "20px", padding: "2px" } })
	        );
	    },

	    componentDidMount: function () {
	        $(ReactDOM.findDOMNode(this.refs.toggleButton)).button();
	    },

	    _onClick: function () {
	        this.props.onClick(this.props.anatomogramId);
	    }
	});

	var AnatomogramSelectImageButtons = React.createClass({
	    displayName: 'AnatomogramSelectImageButtons',

	    propTypes: {
	        selectedId: React.PropTypes.string.isRequired,
	        availableAnatomograms: React.PropTypes.array.isRequired,
	        onClick: React.PropTypes.func.isRequired
	    },

	    render: function () {
	        if (this.props.availableAnatomograms.length > 1) {
	            var selectedId = this.props.selectedId,
	                onClick = this.props.onClick;
	            var anatomogramSelectImageButtons = this.props.availableAnatomograms.map(function (availableAnatomogram) {
	                return React.createElement(AnatomogramSelectImageButton, { key: availableAnatomogram.id + "_toggle",
	                    anatomogramId: availableAnatomogram.id, selected: selectedId === availableAnatomogram.id, toggleSrcTemplate: availableAnatomogram.toggleSrcTemplate, onClick: onClick });
	            });

	            return React.createElement(
	                'span',
	                null,
	                anatomogramSelectImageButtons
	            );
	        } else {
	            return null;
	        }
	    }

	});

	var Anatomogram = React.createClass({
	    displayName: 'Anatomogram',

	    /*
	     E.g. of profileRows:
	     {"id":"ENSMUSG00000029019","name":"Nppb","expressions":[{"factorName":"heart","color":"#C0C0C0","value":"152","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000027350","name":"Chgb","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"#C0C0C0","value":"148","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000033981","name":"Gria2","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"#C0C0C0","value":"140","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000026368","name":"F13b","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"#C0C0C0","value":"136","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000039278","name":"Pcsk1n","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"#C0C0C0","value":"132","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000090298","name":"Gm4794","expressions":[{"factorName":"heart","color":"","value":"","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"#C0C0C0","value":"132","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000002500","name":"Rpl3l","expressions":[{"factorName":"heart","color":"#C0C0C0","value":"127","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     {"id":"ENSMUSG00000029158","name":"Yipf7","expressions":[{"factorName":"heart","color":"#C0C0C0","value":"123","svgPathId":"UBERON_0000948"},{"factorName":"hippocampus","color":"","value":"","svgPathId":"EFO_0000530"},{"factorName":"liver","color":"","value":"","svgPathId":"UBERON_0002107"},{"factorName":"lung","color":"","value":"","svgPathId":"UBERON_0002048"},{"factorName":"spleen","color":"","value":"","svgPathId":"UBERON_0002106"},{"factorName":"thymus","color":"","value":"","svgPathId":"UBERON_0002370"}]},
	     */
	    propTypes: {
	        anatomogramData: React.PropTypes.object.isRequired,
	        expressedTissueColour: React.PropTypes.string.isRequired,
	        hoveredTissueColour: React.PropTypes.string.isRequired,
	        profileRows: React.PropTypes.arrayOf(React.PropTypes.shape({
	            id: React.PropTypes.string,
	            name: React.PropTypes.string.isRequired,
	            expressions: React.PropTypes.arrayOf(React.PropTypes.shape({
	                factorName: React.PropTypes.string,
	                color: React.PropTypes.string,
	                value: React.PropTypes.string.isRequired,
	                svgPathId: React.PropTypes.string
	            })).isRequired
	        })).isRequired,
	        eventEmitter: React.PropTypes.instanceOf(EventEmitter),
	        atlasBaseURL: React.PropTypes.string.isRequired
	    },

	    getInitialState: function () {

	        var availableAnatomograms = [];
	        if (this.props.anatomogramData.maleAnatomogramFile) {
	            availableAnatomograms.push({ id: "male",
	                anatomogramFile: this.props.atlasBaseURL + "/resources/svg/" + this.props.anatomogramData.maleAnatomogramFile,
	                toggleSrcTemplate: this.props.atlasBaseURL + this.props.anatomogramData.toggleButtonMaleImageTemplate });
	        }
	        if (this.props.anatomogramData.femaleAnatomogramFile) {
	            availableAnatomograms.push({ id: "female",
	                anatomogramFile: this.props.atlasBaseURL + "/resources/svg/" + this.props.anatomogramData.femaleAnatomogramFile,
	                toggleSrcTemplate: this.props.atlasBaseURL + this.props.anatomogramData.toggleButtonFemaleImageTemplate });
	        }
	        if (this.props.anatomogramData.brainAnatomogramFile) {
	            availableAnatomograms.push({ id: "brain",
	                anatomogramFile: this.props.atlasBaseURL + "/resources/svg/" + this.props.anatomogramData.brainAnatomogramFile,
	                toggleSrcTemplate: this.props.atlasBaseURL + this.props.anatomogramData.toggleButtonBrainImageTemplate });
	        }

	        var allExpressedFactors = [],
	            expressedFactorsPerRow = {};
	        this.props.profileRows.forEach(function (profileRow) {
	            var expressedFactors = [];
	            profileRow.expressions.forEach(function (expression) {
	                if (expression.value !== "NT" && expression.value !== "") {
	                    expressedFactors.push(expression.svgPathId);
	                }
	            });
	            expressedFactorsPerRow[profileRow.name] = expressedFactors;
	            allExpressedFactors = allExpressedFactors.concat(expressedFactors);
	        });

	        function onlyUnique(value, index, self) {
	            return self.indexOf(value) === index;
	        }

	        return {
	            selectedId: availableAnatomograms[0].id,
	            availableAnatomograms: availableAnatomograms,
	            expressedFactors: allExpressedFactors.filter(onlyUnique),
	            expressedFactorsPerRow: expressedFactorsPerRow,
	            hoveredPathId: null,
	            hoveredRowId: null
	        };
	    },

	    render: function () {
	        function containsHuman(str) {
	            return str.indexOf("human") > -1;
	        }

	        var height = containsHuman(this.props.anatomogramData.maleAnatomogramFile) ? "360" : "250";

	        return React.createElement(
	            'div',
	            { className: 'gxaAnatomogram', style: { display: "table", paddingTop: "4px" } },
	            React.createElement(
	                'div',
	                { style: { display: "table-row" } },
	                React.createElement(
	                    'div',
	                    { style: { display: "table-cell", verticalAlign: "top" } },
	                    React.createElement(AnatomogramSelectImageButtons, { selectedId: this.state.selectedId, availableAnatomograms: this.state.availableAnatomograms, onClick: this._handleChange })
	                ),
	                React.createElement('svg', { ref: 'anatomogram', style: { display: "table-cell", width: "230px", height: height + "px" } })
	            )
	        );
	    },

	    componentDidMount: function () {
	        this.props.eventEmitter.addListener("gxaHeatmapColumnHoverChange", this._highlightPath);
	        this.props.eventEmitter.addListener("gxaHeatmapRowHoverChange", this._highlightRow);
	        this._loadAnatomogram(this._getAnatomogramSVGFile(this.state.selectedId));
	    },

	    // Only displays/highlights the relevant tissues to avoid loading the anatomogram every time we hover over a tissue or a factor header
	    componentDidUpdate: function () {
	        var svg = Snap(ReactDOM.findDOMNode(this.refs.anatomogram)).select("g");
	        this._displayAllOrganismParts(svg);
	    },

	    _handleChange: function (newSelectedId) {
	        if (newSelectedId !== this.state.selectedId) {
	            this._loadAnatomogram(this._getAnatomogramSVGFile(newSelectedId));
	            this.setState({ selectedId: newSelectedId });
	        }
	    },

	    // TODO We could manually highlight un-highlight the affected tissues instead of re-displaying all of them, as setState triggers componentDidUpdate
	    _highlightPath: function (svgPathId) {
	        this.setState({ hoveredPathId: svgPathId });
	    },

	    _highlightRow: function (rowId) {
	        this.setState({ hoveredRowId: rowId });
	    },

	    _getAnatomogramSVGFile: function (id) {
	        for (var i = 0; i < this.state.availableAnatomograms.length; i++) {
	            if (id === this.state.availableAnatomograms[i].id) {
	                return this.state.availableAnatomograms[i].anatomogramFile;
	            }
	        }
	    },

	    _loadAnatomogram: function (svgFile) {

	        var svgCanvas = Snap(ReactDOM.findDOMNode(this.refs.anatomogram)),
	            allElements = svgCanvas.selectAll("*");

	        if (allElements) {
	            allElements.remove();
	        }

	        var displayAllOrganismPartsCallback = this._displayAllOrganismParts;
	        var registerHoverEventsCallback = this._registerHoverEvents;
	        Snap.load(svgFile, function (fragment) {
	            var g = fragment.select("g");
	            g.transform("S1.6,0,0");
	            displayAllOrganismPartsCallback(g);
	            registerHoverEventsCallback(g);
	            svgCanvas.append(g);
	        });
	    },

	    _displayAllOrganismParts: function (svg) {
	        if (svg) {
	            // Sometimes svg is null... why?
	            this.props.anatomogramData.allSvgPathIds.forEach(function (svgPathId) {
	                this._displayOrganismPartsWithDefaultProperties(svg, svgPathId);
	            }, this);
	        }
	    },

	    _hoveredRowContainsPathId: function (svgPathId) {
	        if (!this.state.hoveredRowId) {
	            return false;
	        }

	        return this.state.expressedFactorsPerRow[this.state.hoveredRowId].indexOf(svgPathId) > -1;
	    },

	    _displayOrganismPartsWithDefaultProperties: function (svg, svgPathId) {

	        var colour = this.props.expressedTissueColour;
	        if (this.state.hoveredPathId === svgPathId || this._hoveredRowContainsPathId(svgPathId)) {
	            colour = this.props.hoveredTissueColour;
	        }

	        if (this.state.expressedFactors.indexOf(svgPathId) > -1) {
	            this._highlightOrganismParts(svg, svgPathId, colour, 0.7);
	        } else {
	            this._highlightOrganismParts(svg, svgPathId, "gray", 0.5);
	        }
	    },

	    _highlightOrganismParts: function (svg, svgPathId, colour, opacity) {
	        Anatomogram._recursivelyChangeProperties(svg.select("#" + svgPathId), colour, opacity);
	    },

	    _registerHoverEvents: function (svg) {
	        if (svg) {
	            // Sometimes svg is null... why?

	            var eventEmitter = this.props.eventEmitter,
	                hoverColour = this.props.hoveredTissueColour,
	                highlightOrganismPartsCallback = this._highlightOrganismParts,
	                displayOrganismPartsWithDefaultPropertiesCallback = this._displayOrganismPartsWithDefaultProperties;
	            var mouseoverCallback = function (svgPathId) {
	                highlightOrganismPartsCallback(svg, svgPathId, hoverColour, 0.7);
	                eventEmitter.emit('gxaAnatomogramTissueMouseEnter', svgPathId);
	            };
	            var mouseoutCallback = function (svgPathId) {
	                displayOrganismPartsWithDefaultPropertiesCallback(svg, svgPathId);
	                eventEmitter.emit('gxaAnatomogramTissueMouseLeave', svgPathId);
	            };

	            this.props.anatomogramData.allSvgPathIds.forEach(function (svgPathId) {
	                var svgElement = svg.select("#" + svgPathId);
	                if (svgElement) {
	                    svgElement.mouseover(function () {
	                        mouseoverCallback(svgPathId);
	                    });
	                    svgElement.mouseout(function () {
	                        mouseoutCallback(svgPathId);
	                    });
	                }
	            }, this);
	        }
	    },

	    statics: {
	        _recursivelyChangeProperties: function (svgElement, colour, opacity) {

	            if (svgElement) {
	                var innerElements = svgElement.selectAll("*");

	                if (innerElements.length > 0) {
	                    innerElements.forEach(function (innerElement) {
	                        Anatomogram._recursivelyChangeProperties(innerElement);
	                    });
	                }

	                svgElement.attr({ "fill": colour, "fill-opacity": opacity });
	            }
	        },

	        _recursivelySelectElements: function (svgElement) {
	            if (!svgElement) {
	                return [];
	            }

	            var innerElements = svgElement.selectAll("*");
	            if (innerElements.length === 0) {
	                return [svgElement];
	            } else {
	                var allElements = [];
	                innerElements.forEach(function (innerElement) {
	                    allElements = allElements.concat(Anatomogram._recursivelySelectElements(innerElement));
	                });
	                return allElements;
	            }
	        }
	    }

	});

	//*------------------------------------------------------------------*

	module.exports = Anatomogram;

/***/ },
/* 756 */
[1284, 747],
/* 757 */
[1281, 747],
/* 758 */
584,
/* 759 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);
	var ReactDOMServer = __webpack_require__(760);
	var RadioGroup = __webpack_require__(761);

	var $ = __webpack_require__(747);

	__webpack_require__(757);
	__webpack_require__(762);
	__webpack_require__(749);
	__webpack_require__(763); // Leaks Modernizr to the global window namespace

	__webpack_require__(764)($);
	__webpack_require__(765);

	__webpack_require__(775);
	__webpack_require__(776);
	__webpack_require__(778);

	//*------------------------------------------------------------------*

	var HeatmapBaselineCellVariance = __webpack_require__(780);
	var Legend = __webpack_require__(785);
	var LegendBaseline = Legend.LegendBaseline;
	var LegendDifferential = Legend.LegendDifferential;
	var CellDifferential = __webpack_require__(800);
	var DisplayLevelsButton = __webpack_require__(806);
	var NumberFormat = __webpack_require__(798);
	var HelpTooltips = __webpack_require__(790);
	var ContrastTooltips = __webpack_require__(808);

	var GenePropertiesTooltipModule = __webpack_require__(814);
	var FactorTooltipModule = __webpack_require__(817);

	var StickyHeaderModule = __webpack_require__(821);
	__webpack_require__(822);

	//*------------------------------------------------------------------*

	__webpack_require__(824);

	//*------------------------------------------------------------------*

	var Heatmap = React.createClass({
	    displayName: 'Heatmap',


	    propTypes: {
	        type: React.PropTypes.shape({
	            isBaseline: React.PropTypes.bool,
	            isProteomics: React.PropTypes.bool,
	            isDifferential: React.PropTypes.bool,
	            isMultiExperiment: React.PropTypes.bool,
	            heatmapTooltip: React.PropTypes.string.isRequired
	        }),
	        heatmapConfig: React.PropTypes.object.isRequired,
	        columnHeaders: React.PropTypes.oneOfType([React.PropTypes.arrayOf(React.PropTypes.shape({
	            assayGroupId: React.PropTypes.string.isRequired,
	            factorValue: React.PropTypes.string.isRequired,
	            factorValueOntologyTermId: React.PropTypes.string
	        })), React.PropTypes.arrayOf(React.PropTypes.shape({
	            id: React.PropTypes.string.isRequired,
	            referenceAssayGroup: React.PropTypes.shape({
	                id: React.PropTypes.string.isRequired,
	                assayAccessions: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	                replicates: React.PropTypes.number.isRequired
	            }).isRequired,
	            testAssayGroup: React.PropTypes.shape({
	                id: React.PropTypes.string.isRequired,
	                assayAccessions: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	                replicates: React.PropTypes.number.isRequired
	            }).isRequired,
	            displayName: React.PropTypes.string.isRequired
	        }))]).isRequired,
	        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	        profiles: React.PropTypes.object.isRequired,
	        geneSetProfiles: React.PropTypes.object,
	        prefFormDisplayLevels: React.PropTypes.object,
	        ensemblEventEmitter: React.PropTypes.object,
	        anatomogramEventEmitter: React.PropTypes.object,
	        googleAnalytics: React.PropTypes.bool,
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        linksAtlasBaseURL: React.PropTypes.string.isRequired
	    },

	    getInitialState: function () {
	        var displayLevels = this.props.prefFormDisplayLevels ? this.props.prefFormDisplayLevels.val() === "true" : false;

	        return {
	            showGeneSetProfiles: false,
	            displayLevels: displayLevels,
	            profiles: this.props.profiles,
	            selectedColumnId: null,
	            selectedGeneId: null,
	            hoveredColumnId: null,
	            hoveredGeneId: null,
	            selectedRadioButton: "gradients"
	        };
	    },

	    _hoverColumn: function (columnId) {
	        this.setState({ hoveredColumnId: columnId }, function () {
	            this.props.anatomogramEventEmitter.emit('gxaHeatmapColumnHoverChange', columnId);
	        });
	    },

	    _hoverRow: function (rowId) {
	        this.setState({ hoveredRowId: rowId }, function () {
	            this.props.anatomogramEventEmitter.emit('gxaHeatmapRowHoverChange', rowId);
	        });
	    },

	    selectColumn: function (columnId) {
	        var selectedColumnId = columnId === this.state.selectedColumnId ? null : columnId;
	        this.setState({ selectedColumnId: selectedColumnId }, function () {
	            this.props.ensemblEventEmitter.emit('onColumnSelectionChange', selectedColumnId);
	        });
	    },

	    selectGene: function (geneId) {
	        var selectedGeneId = geneId === this.state.selectedGeneId ? null : geneId;
	        this.setState({ selectedGeneId: selectedGeneId }, function () {
	            this.props.ensemblEventEmitter.emit('onGeneSelectionChange', selectedGeneId);
	        });
	    },

	    toggleGeneSets: function () {
	        var newProfiles = this.state.showGeneSetProfiles ? this.props.profiles : this.props.geneSetProfiles;
	        this.setState({ showGeneSetProfiles: !this.state.showGeneSetProfiles, profiles: newProfiles });
	    },

	    toggleDisplayLevels: function () {
	        var newDisplayLevels = !this.state.displayLevels;
	        this.setState({ displayLevels: newDisplayLevels });
	        if (this.props.prefFormDisplayLevels) {
	            this.props.prefFormDisplayLevels.val(newDisplayLevels);
	        }
	        $(window).resize();
	    },

	    toggleRadioButton: function (newSelected) {
	        this.setState({ selectedRadioButton: newSelected });
	        this.setState({ displayLevels: newSelected === "levels" }); //update the LegendType
	    },

	    isMicroarray: function () {
	        return !(typeof this.props.profiles.rows[0].designElement === "undefined");
	    },

	    hasQuartiles: function () {
	        var hasQuartiles = false;
	        for (var i = 0; i < this.props.profiles.rows[0].expressions.length; i++) {
	            if (this.props.profiles.rows[0].expressions[i].quartiles != undefined) {
	                hasQuartiles = true;
	                break;
	            }
	        }
	        return hasQuartiles;
	    },

	    isSingleGeneResult: function () {
	        return this.props.profiles.rows.length == 1;
	    },

	    componentDidMount: function () {
	        var table = ReactDOM.findDOMNode(this.refs.heatmapTable),
	            stickyIntersect = ReactDOM.findDOMNode(this.refs.stickyIntersect),
	            stickyColumn = ReactDOM.findDOMNode(this.refs.stickyColumn),
	            stickyHeadRow = ReactDOM.findDOMNode(this.refs.stickyHeader),
	            stickyWrap = ReactDOM.findDOMNode(this.refs.stickyWrap),
	            countAndLegend = ReactDOM.findDOMNode(this.refs.countAndLegend);

	        var stickyHeader = StickyHeaderModule(table, stickyIntersect, stickyColumn, stickyHeadRow, stickyWrap, countAndLegend);

	        stickyHeader.setWidthsAndReposition();
	        $(countAndLegend).hcSticky({ bottomEnd: stickyHeader.calculateAllowance() });

	        $(stickyWrap).scroll(stickyHeader.stickyReposition);
	        $(window).resize(stickyHeader.setWidthsAndReposition).scroll(stickyHeader.stickyReposition).on("gxaResizeHeatmapAnatomogramHeader", function () {
	            stickyHeader.setWidthAndHeight();
	            $(countAndLegend).hcSticky("resize");
	        });
	    },

	    legendType: function () {
	        return this.props.type.isBaseline || this.props.type.isMultiExperiment ? React.createElement(LegendBaseline, { atlasBaseURL: this.props.atlasBaseURL,
	            minExpressionLevel: this.state.profiles.minExpressionLevel.toString(),
	            maxExpressionLevel: this.state.profiles.maxExpressionLevel.toString(),
	            isMultiExperiment: this.props.type.isMultiExperiment ? true : false }) : React.createElement(LegendDifferential, { atlasBaseURL: this.props.atlasBaseURL,
	            minDownLevel: this.state.profiles.minDownLevel.toString(),
	            maxDownLevel: this.state.profiles.maxDownLevel.toString(),
	            minUpLevel: this.state.profiles.minUpLevel.toString(),
	            maxUpLevel: this.state.profiles.maxUpLevel.toString() });
	    },

	    render: function () {
	        var paddingMargin = "15px";

	        return React.createElement(
	            'div',
	            null,
	            React.createElement(
	                'div',
	                { ref: 'countAndLegend', className: 'gxaHeatmapCountAndLegend', style: { "paddingBottom": paddingMargin, "position": "sticky" } },
	                React.createElement(
	                    'div',
	                    { style: { display: "inline-block", 'verticalAlign': "top" } },
	                    this.props.type.isMultiExperiment ? React.createElement(
	                        'span',
	                        { id: 'geneCount' },
	                        'Showing ',
	                        this.state.profiles.rows.length,
	                        ' of ',
	                        this.state.profiles.searchResultTotal,
	                        ' experiments found: '
	                    ) : React.createElement(
	                        'span',
	                        { id: 'geneCount' },
	                        'Showing ',
	                        this.state.profiles.rows.length,
	                        ' of ',
	                        this.state.profiles.searchResultTotal,
	                        ' ',
	                        this.state.showGeneSetProfiles ? 'gene sets' : 'genes',
	                        ' found: '
	                    ),
	                    this.props.geneSetProfiles && !this.props.type.isMultiExperiment ? React.createElement(
	                        'a',
	                        { href: 'javascript:void(0)', onClick: this.toggleGeneSets },
	                        this.state.showGeneSetProfiles ? '(show individual genes)' : '(show by gene set)'
	                    ) : ''
	                ),
	                React.createElement(
	                    'div',
	                    { style: { display: "inline-block", "paddingLeft": "10px", "verticalAlign": "top" } },
	                    React.createElement(DownloadProfilesButton, { ref: 'downloadProfilesButton',
	                        downloadProfilesURL: this.props.heatmapConfig.downloadProfilesURL,
	                        atlasBaseURL: this.props.atlasBaseURL })
	                ),
	                React.createElement(
	                    'div',
	                    { style: { display: "inline-block", "paddingLeft": "20px" } },
	                    this.legendType()
	                )
	            ),
	            React.createElement(
	                'div',
	                { ref: 'stickyWrap', className: 'gxaStickyTableWrap', style: { "marginTop": paddingMargin } },
	                React.createElement(
	                    'table',
	                    { ref: 'heatmapTable', className: 'gxaTableGrid gxaStickyEnabled', id: 'heatmap-table' },
	                    React.createElement(HeatmapTableHeader, { ref: 'heatmapTableHeader',
	                        radioId: 'table',
	                        isMicroarray: this.isMicroarray(),
	                        hasQuartiles: this.hasQuartiles(),
	                        isSingleGeneResult: this.isSingleGeneResult(),
	                        type: this.props.type,
	                        columnHeaders: this.props.columnHeaders,
	                        nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	                        multipleColumnHeaders: this.props.multipleColumnHeaders,
	                        selectedColumnId: this.state.selectedColumnId,
	                        selectColumn: this.selectColumn,
	                        hoverColumnCallback: this._hoverColumn,
	                        heatmapConfig: this.props.heatmapConfig,
	                        atlasBaseURL: this.props.atlasBaseURL,
	                        displayLevels: this.state.displayLevels,
	                        toggleDisplayLevels: this.toggleDisplayLevels,
	                        showGeneSetProfiles: this.state.showGeneSetProfiles,
	                        selectedRadioButton: this.state.selectedRadioButton,
	                        toggleRadioButton: this.toggleRadioButton,
	                        renderContrastFactorHeaders: true,
	                        anatomogramEventEmitter: this.props.anatomogramEventEmitter }),
	                    React.createElement(HeatmapTableRows, { profiles: this.state.profiles.rows,
	                        nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	                        selectedGeneId: this.state.selectedGeneId,
	                        selectGene: this.selectGene,
	                        type: this.props.type,
	                        heatmapConfig: this.props.heatmapConfig,
	                        atlasBaseURL: this.props.atlasBaseURL,
	                        linksAtlasBaseURL: this.props.linksAtlasBaseURL,
	                        displayLevels: this.state.displayLevels,
	                        showGeneSetProfiles: this.state.showGeneSetProfiles,
	                        selectedRadioButton: this.state.selectedRadioButton,
	                        hoverColumnCallback: this._hoverColumn,
	                        hoverRowCallback: this._hoverRow,
	                        hasQuartiles: this.hasQuartiles(),
	                        isSingleGeneResult: this.isSingleGeneResult(),
	                        renderExpressionCells: true })
	                ),
	                React.createElement(
	                    'div',
	                    { ref: 'stickyIntersect', className: 'gxaStickyTableIntersect' },
	                    React.createElement(
	                        'table',
	                        { className: 'gxaTableGrid' },
	                        React.createElement(HeatmapTableHeader, { isMicroarray: this.isMicroarray(),
	                            radioId: 'intersect',
	                            hasQuartiles: this.hasQuartiles(),
	                            isSingleGeneResult: this.isSingleGeneResult(),
	                            type: this.props.type,
	                            columnHeaders: this.props.columnHeaders,
	                            nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	                            multipleColumnHeaders: this.props.multipleColumnHeaders,
	                            selectedColumnId: this.state.selectedColumnId,
	                            selectColumn: this.selectColumn,
	                            heatmapConfig: this.props.heatmapConfig,
	                            atlasBaseURL: this.props.atlasBaseURL,
	                            linksAtlasBaseURL: this.props.linksAtlasBaseURL,
	                            displayLevels: this.state.displayLevels,
	                            toggleDisplayLevels: this.toggleDisplayLevels,
	                            showGeneSetProfiles: this.state.showGeneSetProfiles,
	                            selectedRadioButton: this.state.selectedRadioButton,
	                            toggleRadioButton: this.toggleRadioButton,
	                            renderContrastFactorHeaders: false })
	                    )
	                ),
	                React.createElement(
	                    'div',
	                    { ref: 'stickyColumn', className: 'gxaStickyTableColumn' },
	                    React.createElement(
	                        'table',
	                        { className: 'gxaTableGrid' },
	                        React.createElement(HeatmapTableHeader, { isMicroarray: this.isMicroarray(),
	                            radioId: 'column',
	                            hasQuartiles: this.hasQuartiles(),
	                            isSingleGeneResult: this.isSingleGeneResult(),
	                            columnHeaders: this.props.columnHeaders,
	                            nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	                            type: this.props.type,
	                            multipleColumnHeaders: this.props.multipleColumnHeaders,
	                            selectedColumnId: this.state.selectedColumnId,
	                            selectColumn: this.selectColumn,
	                            heatmapConfig: this.props.heatmapConfig,
	                            atlasBaseURL: this.props.atlasBaseURL,
	                            displayLevels: this.state.displayLevels,
	                            toggleDisplayLevels: this.toggleDisplayLevels,
	                            showGeneSetProfiles: this.state.showGeneSetProfiles,
	                            selectedRadioButton: this.state.selectedRadioButton,
	                            toggleRadioButton: this.toggleRadioButton,
	                            renderContrastFactorHeaders: false }),
	                        React.createElement(HeatmapTableRows, { profiles: this.state.profiles.rows,
	                            nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	                            selectedGeneId: this.state.selectedGeneId,
	                            selectGene: this.selectGene,
	                            type: this.props.type,
	                            heatmapConfig: this.props.heatmapConfig,
	                            atlasBaseURL: this.props.atlasBaseURL,
	                            linksAtlasBaseURL: this.props.linksAtlasBaseURL,
	                            displayLevels: this.state.displayLevels,
	                            showGeneSetProfiles: this.state.showGeneSetProfiles,
	                            selectedRadioButton: this.state.selectedRadioButton,
	                            hoverRowCallback: this._hoverRow,
	                            hasQuartiles: this.hasQuartiles(),
	                            isSingleGeneResult: this.isSingleGeneResult(),
	                            renderExpressionCells: false })
	                    )
	                ),
	                React.createElement(
	                    'div',
	                    { ref: 'stickyHeader', className: 'gxaStickyTableHeader' },
	                    React.createElement(
	                        'table',
	                        { className: 'gxaTableGrid' },
	                        React.createElement(HeatmapTableHeader, { isMicroarray: this.isMicroarray(),
	                            radioId: 'header',
	                            hasQuartiles: this.hasQuartiles(),
	                            isSingleGeneResult: this.isSingleGeneResult(),
	                            hoverColumnCallback: this._hoverColumn,
	                            type: this.props.type,
	                            columnHeaders: this.props.columnHeaders,
	                            nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	                            multipleColumnHeaders: this.props.multipleColumnHeaders,
	                            selectedColumnId: this.state.selectedColumnId,
	                            selectColumn: this.selectColumn,
	                            heatmapConfig: this.props.heatmapConfig,
	                            atlasBaseURL: this.props.atlasBaseURL,
	                            linksAtlasBaseURL: this.props.linksAtlasBaseURL,
	                            displayLevels: this.state.displayLevels,
	                            toggleDisplayLevels: this.toggleDisplayLevels,
	                            showGeneSetProfiles: this.state.showGeneSetProfiles,
	                            selectedRadioButton: this.state.selectedRadioButton,
	                            toggleRadioButton: this.toggleRadioButton,
	                            renderContrastFactorHeaders: true,
	                            anatomogramEventEmitter: this.props.anatomogramEventEmitter })
	                    )
	                )
	            )
	        );
	    }

	});

	var DownloadProfilesButton = React.createClass({
	    displayName: 'DownloadProfilesButton',

	    propTypes: {
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        downloadProfilesURL: React.PropTypes.string.isRequired
	    },

	    render: function () {
	        var downloadURL = this.props.atlasBaseURL + this.props.downloadProfilesURL;
	        var downloadImgSrcURL = this.props.atlasBaseURL + "/resources/images/download_blue_small.png";

	        return React.createElement(
	            'a',
	            { id: 'download-profiles-link', ref: 'downloadProfilesLink', className: 'gxaNoTextButton',
	                title: 'Download all results',
	                href: downloadURL, target: '_blank' },
	            React.createElement('img', { id: 'download-profiles', alt: 'Download query results', style: { width: "20px" }, src: downloadImgSrcURL })
	        );
	    },

	    componentDidMount: function () {
	        var $downloadProfilesLink = $(ReactDOM.findDOMNode(this.refs.downloadProfilesLink));
	        $downloadProfilesLink.tooltip({
	            tooltipClass: "gxaHelpTooltip"
	        });
	        $downloadProfilesLink.button();
	        $downloadProfilesLink.addClass("gxaNoTextButton");
	    }
	});

	var HeatmapTableHeader = React.createClass({
	    displayName: 'HeatmapTableHeader',

	    propTypes: {
	        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string)
	    },

	    renderContrastFactorHeaders: function () {
	        var heatmapConfig = this.props.heatmapConfig;
	        if (this.props.type.isBaseline) {
	            return renderFactorHeaders(heatmapConfig, this.props.atlasBaseURL, this.props.mainHeaderNames, this.props.type, this.props.columnHeaders, this.props.nonExpressedColumnHeaders, heatmapConfig.experimentAccession, this.props.selectColumn, this.props.selectedColumnId, this.props.hoverColumnCallback, this.props.anatomogramEventEmitter);
	        } else if (this.props.type.isDifferential) {
	            return React.createElement(ContrastHeaders, { heatmapConfig: heatmapConfig,
	                atlasBaseURL: this.props.atlasBaseURL,
	                contrasts: this.props.columnHeaders,
	                selectedColumnId: this.props.selectedColumnId,
	                selectColumn: this.props.selectColumn,
	                experimentAccession: heatmapConfig.experimentAccession,
	                showMaPlotButton: heatmapConfig.showMaPlotButton,
	                gseaPlots: heatmapConfig.gseaPlots });
	        } else if (this.props.type.isMultiExperiment) {
	            return renderFactorHeaders(heatmapConfig, this.props.atlasBaseURL, null, this.props.type, this.props.columnHeaders, this.props.nonExpressedColumnHeaders, "", this.props.selectColumn, this.props.selectedColumnId, this.props.hoverColumnCallback, this.props.anatomogramEventEmitter);
	        }
	    },

	    render: function () {
	        var showGeneProfile = this.props.showGeneSetProfiles ? "Gene set" : "Gene";
	        var showExperimentProfile = this.props.type.isMultiExperiment ? "Experiment" : showGeneProfile;

	        return React.createElement(
	            'thead',
	            null,
	            React.createElement(
	                'tr',
	                null,
	                React.createElement(
	                    'th',
	                    { className: 'gxaHorizontalHeaderCell gxaHeatmapTableIntersect', colSpan: this.props.isMicroarray ? 2 : undefined },
	                    React.createElement(TopLeftCorner, { type: this.props.type,
	                        hasQuartiles: this.props.hasQuartiles,
	                        radioId: this.props.radioId,
	                        isSingleGeneResult: this.props.isSingleGeneResult,
	                        heatmapConfig: this.props.heatmapConfig,
	                        displayLevels: this.props.displayLevels,
	                        toggleDisplayLevels: this.props.toggleDisplayLevels,
	                        selectedRadioButton: this.props.selectedRadioButton,
	                        toggleRadioButton: this.props.toggleRadioButton,
	                        atlasBaseURL: this.props.atlasBaseURL })
	                ),
	                this.props.renderContrastFactorHeaders ? this.renderContrastFactorHeaders() : null
	            ),
	            React.createElement(
	                'tr',
	                null,
	                React.createElement(
	                    'th',
	                    { className: 'gxaHorizontalHeaderCell gxaHeatmapTableIntersect', style: this.props.isMicroarray ? { width: "166px" } : {} },
	                    React.createElement(
	                        'div',
	                        null,
	                        showExperimentProfile
	                    )
	                ),
	                this.props.isMicroarray ? React.createElement(
	                    'th',
	                    { className: 'gxaHorizontalHeaderCell gxaHeatmapTableIntersect' },
	                    React.createElement(
	                        'div',
	                        null,
	                        'Design Element'
	                    )
	                ) : null
	            )
	        );
	    }
	});

	function restrictLabelSize(label, maxSize) {
	    var result = label;
	    if (result.length > maxSize + 1) {
	        // +1 to account for the extra ellipsis character appended
	        result = result.substring(0, maxSize);
	        if (result.lastIndexOf(" ") > maxSize - 5) {
	            result = result.substring(0, result.lastIndexOf(" "));
	        }
	        result = result + "…";
	    }
	    return result;
	}

	function renderFactorHeaders(heatmapConfig, atlasBaseURL, mainHeaderNames, type, assayGroupFactors, nonExpressedGroupFactors, experimentAccession, selectColumn, selectedColumnId, hoverColumnCallback, anatomogramEventEmitter) {

	    return assayGroupFactors.map(function (assayGroupFactor) {
	        return React.createElement(FactorHeader, { key: mainHeaderNames + assayGroupFactor.factorValue,
	            type: type,
	            heatmapConfig: heatmapConfig,
	            factorName: assayGroupFactor.factorValue,
	            svgPathId: assayGroupFactor.factorValueOntologyTermId,
	            assayGroupId: assayGroupFactor.assayGroupId,
	            experimentAccession: experimentAccession,
	            selectColumn: selectColumn,
	            selected: assayGroupFactor.assayGroupId === selectedColumnId,
	            hoverColumnCallback: hoverColumnCallback,
	            anatomogramEventEmitter: anatomogramEventEmitter,
	            atlasBaseURL: atlasBaseURL });
	    });
	}

	var FactorHeader = React.createClass({
	    displayName: 'FactorHeader',


	    getInitialState: function () {
	        return { hover: false, selected: false };
	    },

	    onMouseEnter: function () {
	        if (this.props.heatmapConfig.enableEnsemblLauncher) {
	            this.setState({ hover: true });
	        }
	        this.props.hoverColumnCallback(this.props.svgPathId);
	    },

	    onMouseLeave: function () {
	        if (this.props.heatmapConfig.enableEnsemblLauncher) {
	            this.setState({ hover: false });
	        }
	        this.props.hoverColumnCallback(null);
	        this._closeTooltip();
	    },

	    _closeTooltip: function () {
	        if (!this.props.type.isMultiExperiment) {
	            $(ReactDOM.findDOMNode(this)).tooltip("close");
	        }
	    },

	    _anatomogramTissueMouseEnter: function (svgPathId) {
	        if (svgPathId === this.props.svgPathId) {
	            $(ReactDOM.findDOMNode(this.refs.headerCell)).addClass("gxaHeaderHover");
	        }
	    },

	    _anatomogramTissueMouseLeave: function (svgPathId) {
	        if (svgPathId === this.props.svgPathId) {
	            $(ReactDOM.findDOMNode(this.refs.headerCell)).removeClass("gxaHeaderHover");
	        }
	    },

	    onClick: function () {
	        if (this.props.heatmapConfig.enableEnsemblLauncher) {
	            this.props.selectColumn(this.props.assayGroupId);
	        }
	    },

	    componentDidMount: function () {
	        if (!this.props.type.isMultiExperiment) {
	            FactorTooltipModule.init(this.props.atlasBaseURL, this.props.heatmapConfig.accessKey, ReactDOM.findDOMNode(this), this.props.experimentAccession, this.props.assayGroupId);
	        }
	        if (this.props.anatomogramEventEmitter) {
	            this.props.anatomogramEventEmitter.addListener('gxaAnatomogramTissueMouseEnter', this._anatomogramTissueMouseEnter);
	            this.props.anatomogramEventEmitter.addListener('gxaAnatomogramTissueMouseLeave', this._anatomogramTissueMouseLeave);
	        }
	    },

	    render: function () {
	        var showSelectTextOnHover = this.state.hover && !this.props.selected ? React.createElement(
	            'span',
	            { style: { position: "absolute", width: "10px", right: "0px", left: "95px", float: "right", color: "green" } },
	            '  select'
	        ) : null;
	        var showTickWhenSelected = this.props.selected ? React.createElement(
	            'span',
	            { className: 'rotate_tick', style: { position: "absolute", width: "5px", right: "0px", left: "125px", float: "right", color: "green" } },
	            ' ✔ '
	        ) : null;
	        var thClass = "rotated_cell gxaHoverableHeader" + (this.props.selected ? " gxaVerticalHeaderCell-selected" : " gxaVerticalHeaderCell") + (this.props.heatmapConfig.enableEnsemblLauncher ? " gxaSelectableHeader" : "");
	        var divClass = "rotate_text factor-header";
	        var factorName = Modernizr.csstransforms ? restrictLabelSize(this.props.factorName, 14) : this.props.factorName;

	        return React.createElement(
	            'th',
	            { ref: 'headerCell', className: thClass, onMouseEnter: this.onMouseEnter, onMouseLeave: this.onMouseLeave, onClick: this.onClick, rowSpan: '2' },
	            React.createElement(
	                'div',
	                { 'data-assay-group-id': this.props.assayGroupId, 'data-experiment-accession': this.props.experimentAccession, className: divClass },
	                factorName,
	                showSelectTextOnHover,
	                showTickWhenSelected
	            )
	        );
	    }

	});

	var ContrastHeaders = React.createClass({
	    displayName: 'ContrastHeaders',


	    render: function () {
	        var heatmapConfig = this.props.heatmapConfig;

	        var contrastHeaders = this.props.contrasts.map(function (contrast) {
	            var gseaPlotsThisContrast = this.props.gseaPlots ? this.props.gseaPlots[contrast.id] : { go: false, interpro: false, reactome: false };
	            return React.createElement(ContrastHeader, { key: contrast.id,
	                heatmapConfig: heatmapConfig,
	                atlasBaseURL: this.props.atlasBaseURL,
	                selectColumn: this.props.selectColumn,
	                selected: contrast.id === this.props.selectedColumnId,
	                contrastName: contrast.displayName, arrayDesignAccession: contrast.arrayDesignAccession,
	                contrastId: contrast.id, experimentAccession: this.props.experimentAccession,
	                showMaPlotButton: this.props.showMaPlotButton,
	                showGseaGoPlot: gseaPlotsThisContrast.go,
	                showGseaInterproPlot: gseaPlotsThisContrast.interpro,
	                showGseaReactomePlot: gseaPlotsThisContrast.reactome });
	        }.bind(this));

	        return React.createElement(
	            'div',
	            null,
	            contrastHeaders
	        );
	    }

	});

	var ContrastHeader = React.createClass({
	    displayName: 'ContrastHeader',


	    getInitialState: function () {
	        return { hover: false, selected: false };
	    },

	    onMouseEnter: function () {
	        this.setState({ hover: true });
	    },

	    onMouseLeave: function () {
	        this.setState({ hover: false });
	        this._closeTooltip();
	    },

	    _closeTooltip: function () {
	        $(ReactDOM.findDOMNode(this)).tooltip("close");
	    },

	    onClick: function () {
	        this.props.selectColumn(this.props.contrastId);
	    },

	    componentDidMount: function () {
	        ContrastTooltips(this.props.atlasBaseURL, this.props.heatmapConfig.accessKey, ReactDOM.findDOMNode(this), this.props.experimentAccession, this.props.contrastId);

	        if (this.showPlotsButton()) {
	            this.renderToolBarContent(ReactDOM.findDOMNode(this.refs.plotsToolBarContent));

	            var $plotsButton = $(ReactDOM.findDOMNode(this.refs.plotsButton));
	            $plotsButton.tooltip({
	                hide: false,
	                show: false,
	                tooltipClass: "gxaHelpTooltip"
	            }).button();
	            $plotsButton.toolbar({
	                content: ReactDOM.findDOMNode(this.refs.plotsToolBarContent),
	                position: "right",
	                style: "white",
	                event: "click",
	                hideOnClick: true
	            });
	            $plotsButton.addClass("gxaNoTextButton");
	        }
	    },

	    renderToolBarContent: function (contentNode) {

	        var $contentNode = $(contentNode);

	        var maPlotURL = this.props.atlasBaseURL + "/external-resources/" + this.props.experimentAccession + '/' + (this.props.arrayDesignAccession ? this.props.arrayDesignAccession + "/" : "") + this.props.contrastId + "/ma-plot.png";
	        var maPlotImgSrcURL = this.props.atlasBaseURL + "/resources/images/maplot-button.png";

	        var gseaGoPlotURL = this.props.atlasBaseURL + "/external-resources/" + this.props.experimentAccession + '/' + this.props.contrastId + "/gsea_go.png";
	        var gseaGoPlotImgSrcURL = this.props.atlasBaseURL + "/resources/images/gsea-go-button.png";

	        var gseaInterproPlotURL = this.props.atlasBaseURL + "/external-resources/" + this.props.experimentAccession + '/' + this.props.contrastId + "/gsea_interpro.png";
	        var gseaInterproImgSrcURL = this.props.atlasBaseURL + '/resources/images/gsea-interpro-button.png';

	        var gseaReactomePlotURL = this.props.atlasBaseURL + "/external-resources/" + this.props.experimentAccession + '/' + this.props.contrastId + "/gsea_reactome.png";
	        var gseaReactomePlotImgSrcURL = this.props.atlasBaseURL + "/resources/images/gsea-reactome-button.png";

	        var content = React.createElement(
	            'div',
	            null,
	            this.props.showMaPlotButton ? React.createElement(
	                'a',
	                { href: maPlotURL, id: 'maButtonID', title: 'Click to view MA plot for the contrast across all genes', onClick: this.clickButton },
	                React.createElement('img', { src: maPlotImgSrcURL })
	            ) : null,
	            this.props.showGseaGoPlot ? React.createElement(
	                'a',
	                { href: gseaGoPlotURL, id: 'goButtonID', title: 'Click to view GO terms enrichment analysis plot', onClick: this.clickButton },
	                React.createElement('img', { src: gseaGoPlotImgSrcURL })
	            ) : null,
	            this.props.showGseaInterproPlot ? React.createElement(
	                'a',
	                { href: gseaInterproPlotURL, id: 'interproButtonID', title: 'Click to view Interpro domains enrichment analysis plot', onClick: this.clickButton },
	                React.createElement('img', { src: gseaInterproImgSrcURL })
	            ) : null,
	            this.props.showGseaReactomePlot ? React.createElement(
	                'a',
	                { href: gseaReactomePlotURL, id: 'reactomeButtonID', title: 'Click to view Reactome pathways enrichment analysis plot', onClick: this.clickButton },
	                React.createElement('img', { src: gseaReactomePlotImgSrcURL })
	            ) : null
	        );

	        // the tool bar content will be copied around the DOM by the toolbar plugin
	        // so we render using static markup because otherwise when copied, we'll end up with
	        // duplicate data-reactids
	        $contentNode.html(ReactDOMServer.renderToStaticMarkup(content));

	        $contentNode.find('a').tooltip({
	            tooltipClass: "gxaHelpTooltip"
	        });

	        //need to use each here otherwise we get a fancybox error
	        $contentNode.find('a').each(function (index, button) {
	            $(button).fancybox({
	                padding: 0,
	                openEffect: 'elastic',
	                closeEffect: 'elastic'
	            });
	        });
	    },

	    clickButton: function (event) {
	        // prevent contrast from being selected
	        event.stopPropagation();
	    },

	    showPlotsButton: function () {
	        return this.props.showMaPlotButton || this.props.showGseaGoPlot || this.props.showGseaInterproPlot || this.props.showGseaReactomePlot;
	    },

	    render: function () {
	        var thStyle = this.showPlotsButton() ? { minWidth: "80px" } : {};
	        var textStyle = this.showPlotsButton() ? { top: "57px" } : {};

	        var plotsImgSrcURL = this.props.atlasBaseURL + "/resources/images/yellow-chart-icon.png";

	        var plotsButton = React.createElement(
	            'div',
	            { style: { textAlign: "right", paddingRight: "3px" } },
	            React.createElement(
	                'a',
	                { href: '#', ref: 'plotsButton', onClick: this.clickButton, title: 'Click to view plots' },
	                React.createElement('img', { src: plotsImgSrcURL })
	            )
	        );

	        var showSelectTextOnHover = this.state.hover && !this.props.selected ? React.createElement(
	            'span',
	            { style: { position: "absolute", width: "10px", right: "0px", left: "95px", bottom: "-35px", color: "green" } },
	            '  select'
	        ) : null;
	        var showTickWhenSelected = this.props.selected ? React.createElement(
	            'span',
	            { className: 'rotate_tick', style: { position: "absolute", width: "5px", right: "0px", left: "125px", bottom: "-35px", color: "green" } },
	            ' ✔ '
	        ) : null;
	        var thClass = "rotated_cell gxaHoverableHeader" + (this.props.selected ? " gxaVerticalHeaderCell-selected" : " gxaVerticalHeaderCell") + (this.props.heatmapConfig.enableEnsemblLauncher ? " gxaSelectableHeader " : "");
	        var divClass = "rotate_text factor-header";
	        var contrastName = Modernizr.csstransforms ? restrictLabelSize(this.props.contrastName, 17) : this.props.contrastName;

	        return React.createElement(
	            'th',
	            { className: thClass, rowSpan: '2', style: thStyle, onMouseEnter: this.props.heatmapConfig.enableEnsemblLauncher ? this.onMouseEnter : undefined, onMouseLeave: this.props.heatmapConfig.enableEnsemblLauncher ? this.onMouseLeave : this._closeTooltip, onClick: this.props.heatmapConfig.enableEnsemblLauncher ? this.onClick : undefined },
	            React.createElement(
	                'div',
	                { 'data-contrast-id': this.props.contrastId, 'data-experiment-accession': this.props.experimentAccession, className: divClass, style: textStyle },
	                contrastName,
	                showSelectTextOnHover,
	                showTickWhenSelected
	            ),
	            this.showPlotsButton() ? plotsButton : null,
	            this.showPlotsButton() ? React.createElement(
	                'div',
	                { ref: 'plotsToolBarContent', style: { display: "none" } },
	                'placeholder'
	            ) : null
	        );
	    }

	});

	var TopLeftCorner = React.createClass({
	    displayName: 'TopLeftCorner',


	    displayLevelsBaseline: function () {
	        if (this.props.hasQuartiles && this.props.isSingleGeneResult) {
	            return React.createElement(LevelsRadioGroup, { radioId: this.props.radioId,
	                selectedRadioButton: this.props.selectedRadioButton,
	                toggleRadioButton: this.props.toggleRadioButton });
	        } else if (this.props.type.isBaseline || this.props.type.isMultiExperiment) {
	            return React.createElement(DisplayLevelsButton, { hideText: 'Hide levels',
	                showText: 'Display levels',
	                onClickCallback: this.props.toggleDisplayLevels,
	                displayLevels: this.props.displayLevels,
	                width: '150px', fontSize: '14px' });
	        } else {
	            return React.createElement(DisplayLevelsButton, { hideText: 'Hide log<sub>2</sub>-fold change',
	                showText: 'Display log<sub>2</sub>-fold change',
	                onClickCallback: this.props.toggleDisplayLevels,
	                displayLevels: this.props.displayLevels,
	                width: '200px', fontSize: '14px' });
	        }
	    },

	    render: function () {
	        return React.createElement(
	            'div',
	            { className: 'gxaHeatmapMatrixTopLeftCorner' },
	            React.createElement('span', { 'data-help-loc': this.props.type.heatmapTooltip, ref: 'tooltipSpan' }),
	            React.createElement(
	                'div',
	                { style: { display: "table-cell", verticalAlign: "middle", textAlign: "center" } },
	                this.displayLevelsBaseline()
	            )
	        );
	    },

	    componentDidMount: function () {
	        HelpTooltips(this.props.atlasBaseURL, 'experiment', ReactDOM.findDOMNode(this.refs.tooltipSpan));
	    }

	});

	var LevelsRadioGroup = React.createClass({
	    displayName: 'LevelsRadioGroup',


	    getInitialState: function () {
	        return { value: this.props.selectedRadioButton };
	    },

	    render: function () {
	        return React.createElement(
	            RadioGroup,
	            { name: "displayLevelsGroup_" + this.props.radioId, value: this.props.selectedRadioButton, onChange: this.handleChange },
	            React.createElement(
	                'div',
	                { style: { "marginLeft": "10px", "marginTop": "8px" } },
	                React.createElement('input', { type: 'radio', value: 'gradients' }),
	                'Display gradients',
	                React.createElement('br', null),
	                React.createElement('input', { type: 'radio', value: 'levels' }),
	                'Display levels',
	                React.createElement('br', null),
	                React.createElement('input', { type: 'radio', value: 'variance' }),
	                'Display variance'
	            )
	        );
	    },

	    handleChange: function (event) {
	        this.props.toggleRadioButton(event.target.value);
	        this.setState({ value: this.props.selectedRadioButton });

	        // To resize the sticky column/header in case the row height or column width changes
	        $(window).resize();
	    }
	});

	var HeatmapTableRows = React.createClass({
	    displayName: 'HeatmapTableRows',

	    propTypes: {
	        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string)
	    },

	    profileRowType: function (profile) {
	        var geneProfileKey = this.props.heatmapConfig.species + "-" + (this.props.type.isDifferential ? profile.name + "-" + profile.designElement : profile.name);
	        return this.props.type.isMultiExperiment ? React.createElement(GeneProfileRow, { key: geneProfileKey,
	            id: profile.id,
	            name: profile.name,
	            type: this.props.type,
	            experimentType: profile.experimentType,
	            expressions: profile.expressions,
	            nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	            serializedFilterFactors: profile.serializedFilterFactors,
	            heatmapConfig: this.props.heatmapConfig,
	            atlasBaseURL: this.props.atlasBaseURL,
	            linksAtlasBaseURL: this.props.linksAtlasBaseURL,
	            displayLevels: this.props.displayLevels,
	            renderExpressionCells: this.props.renderExpressionCells,
	            hoverColumnCallback: this.props.hoverColumnCallback,
	            hoverRowCallback: this.props.hoverRowCallback }) : React.createElement(GeneProfileRow, { key: geneProfileKey,
	            selected: profile.id === this.props.selectedGeneId,
	            selectGene: this.props.selectGene,
	            designElement: profile.designElement,
	            id: profile.id,
	            name: profile.name,
	            type: this.props.type,
	            expressions: profile.expressions,
	            nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	            heatmapConfig: this.props.heatmapConfig,
	            atlasBaseURL: this.props.atlasBaseURL,
	            linksAtlasBaseURL: this.props.linksAtlasBaseURL,
	            displayLevels: this.props.displayLevels,
	            showGeneSetProfiles: this.props.showGeneSetProfiles,
	            selectedRadioButton: this.props.selectedRadioButton,
	            hasQuartiles: this.props.hasQuartiles,
	            isSingleGeneResult: this.props.isSingleGeneResult,
	            renderExpressionCells: this.props.renderExpressionCells,
	            hoverColumnCallback: this.props.hoverColumnCallback,
	            hoverRowCallback: this.props.hoverRowCallback });
	    },

	    render: function () {
	        var geneProfilesRows = this.props.profiles.map(function (profile) {

	            return this.profileRowType(profile);
	        }.bind(this));

	        return React.createElement(
	            'tbody',
	            null,
	            geneProfilesRows
	        );
	    }
	});

	var GeneProfileRow = React.createClass({
	    displayName: 'GeneProfileRow',

	    propTypes: {
	        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string),
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        linksAtlasBaseURL: React.PropTypes.string.isRequired
	    },

	    getInitialState: function () {
	        return { hover: false, selected: false, levels: this.props.displayLevels };
	    },

	    onMouseEnter: function () {
	        if (this.props.heatmapConfig.enableEnsemblLauncher) {
	            this.setState({ hover: true });
	        }
	        // We use name instead of id because in multiexperiment the same id can appear under different name (same experiment, different conditions)
	        this.props.hoverRowCallback(this.props.name);
	    },

	    onMouseLeave: function () {
	        if (this.props.heatmapConfig.enableEnsemblLauncher) {
	            this.setState({ hover: false });
	        }
	        this._closeTooltip();
	        this.props.hoverRowCallback(null);
	    },

	    onClick: function () {
	        if (this.props.heatmapConfig.enableEnsemblLauncher) {
	            this.props.selectGene(this.props.id);
	        }
	    },

	    geneNameLinked: function () {
	        var experimentURL = '/experiments/' + this.props.id + '?geneQuery=' + this.props.heatmapConfig.geneQuery + (this.props.serializedFilterFactors ? "&serializedFilterFactors=" + encodeURIComponent(this.props.serializedFilterFactors) : "");
	        var geneURL = this.props.showGeneSetProfiles ? '/query?geneQuery=' + this.props.name + '&exactMatch=' + this.props.heatmapConfig.isExactMatch : '/genes/' + this.props.id;

	        var titleTooltip = this.props.type.isMultiExperiment ? this.props.experimentType == "PROTEOMICS_BASELINE" ? "Protein Expression" : "RNA Expression" : "";

	        var experimentOrGeneURL = this.props.linksAtlasBaseURL + (this.props.type.isMultiExperiment ? experimentURL : geneURL);

	        // don't render id for gene sets to prevent tooltips
	        // The vertical align in the <a> element is needed because the kerning in the font used in icon-conceptual is vertically off
	        return React.createElement(
	            'span',
	            { title: titleTooltip, style: { "display": "table-cell" } },
	            React.createElement('span', { className: 'icon icon-conceptual icon-c2', 'data-icon': this.props.type.isMultiExperiment ? this.props.experimentType == "PROTEOMICS_BASELINE" ? 'P' : 'd' : '' }),
	            React.createElement(
	                'a',
	                { ref: 'geneName', id: this.props.showGeneSetProfiles ? '' : this.props.id, href: experimentOrGeneURL, onClick: this.geneNameLinkClicked, style: { "verticalAlign": "15%" } },
	                this.props.name
	            )
	        );
	    },

	    geneNameLinkClicked: function (event) {
	        // prevent row from being selected
	        event.stopPropagation();
	    },

	    geneNameNotLinked: function () {
	        // don't render id for gene sets to prevent tooltips
	        return React.createElement(
	            'span',
	            { style: { "float": "left" }, ref: 'geneName', title: '', id: this.props.showGeneSetProfiles ? '' : this.props.id },
	            this.props.name
	        );
	    },

	    displayLevelsRadio: function () {
	        if (this.props.hasQuartiles && this.props.isSingleGeneResult) {
	            return this.props.selectedRadioButton === "levels";
	        } else return this.props.displayLevels;
	    },

	    cellType: function (expression) {
	        if (this.props.type.isBaseline) {
	            if (this.props.selectedRadioButton === "variance" && expression.quartiles) {
	                return React.createElement(HeatmapBaselineCellVariance, { key: this.props.id + expression.factorName,
	                    quartiles: expression.quartiles,
	                    hoverColumnCallback: this.props.hoverColumnCallback });
	            } else {
	                return React.createElement(CellBaseline, { key: this.props.id + expression.factorName,
	                    factorName: expression.factorName,
	                    color: expression.color,
	                    value: expression.value,
	                    heatmapConfig: this.props.heatmapConfig,
	                    displayLevels: this.displayLevelsRadio(),
	                    svgPathId: expression.svgPathId,
	                    geneSetProfiles: this.props.showGeneSetProfiles,
	                    id: this.props.id,
	                    name: this.props.name,
	                    hoverColumnCallback: this.props.hoverColumnCallback });
	            }
	        } else if (this.props.type.isDifferential) {
	            return React.createElement(CellDifferential, { key: this.props.designElement + this.props.name + expression.contrastName,
	                colour: expression.color,
	                foldChange: expression.foldChange,
	                pValue: expression.pValue,
	                tStat: expression.tStat,
	                displayLevels: this.props.displayLevels });
	        } else if (this.props.type.isMultiExperiment) {
	            return React.createElement(CellMultiExperiment, { key: this.props.id + expression.factorName,
	                factorName: expression.factorName,
	                serializedFilterFactors: this.props.serializedFilterFactors,
	                color: expression.color,
	                value: expression.value,
	                displayLevels: this.props.displayLevels,
	                svgPathId: expression.svgPathId,
	                id: this.props.id,
	                name: this.props.name,
	                hoverColumnCallback: this.props.hoverColumnCallback });
	        }
	    },

	    cells: function (expressions, nonExpressedColumnHeaders) {
	        //var filteredExpressions = expressions.filter(function(expression) {
	        //    return (nonExpressedColumnHeaders.indexOf(expression.factorName) == -1)
	        //});
	        //
	        //return filteredExpressions.map(function (expression) {
	        //    return this.cellType(expression);
	        //}.bind(this));

	        return expressions.map(function (expression) {
	            return this.cellType(expression);
	        }.bind(this));
	    },

	    render: function () {
	        var showSelectTextOnHover = this.state.hover && !this.props.selected ? React.createElement(
	            'span',
	            { style: { "display": "table-cell", "textAlign": "right", "paddingLeft": "10px", "color": "green", "visibility": "visible" } },
	            'select'
	        ) : React.createElement(
	            'span',
	            { style: { "display": "table-cell", "textAlign": "right", "paddingLeft": "10px", "color": "green", "visibility": "hidden" } },
	            'select'
	        );
	        var showTickWhenSelected = this.props.selected ? React.createElement(
	            'span',
	            { style: { "float": "right", "color": "green" } },
	            ' ✔ '
	        ) : null;
	        var className = (this.props.selected ? "gxaHorizontalHeaderCell-selected gxaHoverableHeader" : "gxaHorizontalHeaderCell gxaHoverableHeader") + (this.props.heatmapConfig.enableEnsemblLauncher ? " gxaSelectableHeader" : "");
	        var rowClassName = this.props.type.isMultiExperiment ? this.props.experimentType == "PROTEOMICS_BASELINE" ? "gxaProteomicsExperiment" : "gxaTranscriptomicsExperiment" : "";

	        return React.createElement(
	            'tr',
	            { className: rowClassName },
	            React.createElement(
	                'th',
	                { className: className, onMouseEnter: this.onMouseEnter, onMouseLeave: this.onMouseLeave, onClick: this.onClick },
	                React.createElement(
	                    'div',
	                    { style: { display: "table", width: "100%" } },
	                    React.createElement(
	                        'div',
	                        { style: { display: "table-row" } },
	                        this.props.heatmapConfig.enableGeneLinks ? this.geneNameLinked() : this.geneNameNotLinked(),
	                        this.props.heatmapConfig.enableEnsemblLauncher ? showSelectTextOnHover : null,
	                        this.props.heatmapConfig.enableEnsemblLauncher ? showTickWhenSelected : null
	                    )
	                )
	            ),
	            this.props.designElement ? React.createElement(
	                'th',
	                { className: 'gxaHeatmapTableDesignElement' },
	                this.props.designElement
	            ) : null,
	            this.props.renderExpressionCells ? this.cells(this.props.expressions, this.props.nonExpressedColumnHeaders) : null
	        );
	    },

	    componentDidMount: function () {
	        if (!this.props.type.isMultiExperiment) {
	            GenePropertiesTooltipModule.init(this.props.atlasBaseURL, ReactDOM.findDOMNode(this.refs.geneName), this.props.id, this.props.name);
	        }
	    },

	    _closeTooltip: function () {
	        if (!this.props.type.isMultiExperiment) {
	            $(ReactDOM.findDOMNode(this.refs.geneName)).tooltip("close");
	        }
	    }

	});

	var CellBaseline = React.createClass({
	    displayName: 'CellBaseline',

	    render: function () {
	        if (this._noExpression()) {
	            return React.createElement('td', null);
	        }

	        var style = { "backgroundColor": this._isUnknownExpression() ? "white" : this.props.color };

	        return React.createElement(
	            'td',
	            { style: style, onMouseEnter: this._onMouseEnter, onMouseLeave: this._onMouseLeave },
	            React.createElement(
	                'div',
	                {
	                    className: 'gxaHeatmapCell',
	                    style: { visibility: this._isUnknownExpression() || this.props.displayLevels ? "visible" : "hidden" } },
	                this._isUnknownExpression() ? this._unknownCell() : NumberFormat.baselineExpression(this.props.value)
	            )
	        );
	    },

	    componentDidMount: function () {
	        this.addQuestionMarkTooltip();
	    },

	    // need this so that we re-add question mark tooltip, if it doesn't exist, when switching between
	    // individual genes and gene sets
	    componentDidUpdate: function () {
	        this.addQuestionMarkTooltip();
	    },

	    addQuestionMarkTooltip: function () {
	        function hasQuestionMark(unknownElement) {
	            return unknownElement.children.length;
	        }

	        if (this._isUnknownExpression() && !hasQuestionMark(ReactDOM.findDOMNode(this.refs.unknownCell))) {
	            HelpTooltips(this.props.atlasBaseURL, 'experiment', ReactDOM.findDOMNode(this.refs.unknownCell));
	        }
	    },

	    _hasKnownExpression: function () {
	        // true if not blank or UNKNOWN, ie: has a expression with a known value
	        return this.props.value && !this._isUnknownExpression();
	    },

	    _isUnknownExpression: function () {
	        return this.propsvalue === "UNKNOWN";
	    },

	    _noExpression: function () {
	        return !this.props.value;
	    },

	    _unknownCell: function () {
	        return React.createElement('span', { ref: 'unknownCell', 'data-help-loc': this.props.geneSetProfiles ? '#heatMapTableGeneSetUnknownCell' : '#heatMapTableUnknownCell' });
	    },

	    _onMouseEnter: function () {
	        if (this._hasKnownExpression()) {
	            this.props.hoverColumnCallback(this.props.svgPathId);
	        }
	    },

	    _onMouseLeave: function () {
	        if (this._hasKnownExpression()) {
	            this.props.hoverColumnCallback(null);
	        }
	    }
	});

	var CellMultiExperiment = React.createClass({
	    displayName: 'CellMultiExperiment',

	    _isNAExpression: function () {
	        return this.props.value === "NT";
	    },

	    _noExpression: function () {
	        return !this.props.value;
	    },

	    _tissueNotStudiedInExperiment: function () {
	        return React.createElement(
	            'span',
	            null,
	            'NA'
	        );
	    },

	    _onMouseEnter: function () {
	        if (!this._noExpression() && !this._isNAExpression()) {
	            this.props.hoverColumnCallback(this.props.svgPathId);
	        }
	    },

	    _onMouseLeave: function () {
	        if (!this._noExpression() && !this._isNAExpression()) {
	            this.props.hoverColumnCallback(null);
	        }
	    },

	    render: function () {

	        if (this._noExpression()) {
	            return React.createElement('td', null);
	        }

	        var style = { "backgroundColor": this.props.color };

	        return React.createElement(
	            'td',
	            { style: style, onMouseEnter: this._onMouseEnter, onMouseLeave: this._onMouseLeave },
	            React.createElement(
	                'div',
	                { className: 'gxaHeatmapCell', style: { visibility: this._isNAExpression() || this.props.displayLevels ? "visible" : "hidden" } },
	                this._isNAExpression(this.props.value) ? this._tissueNotStudiedInExperiment() : NumberFormat.baselineExpression(this.props.value)
	            )
	        );
	    }
	});

	//*------------------------------------------------------------------*

	module.exports = Heatmap;

/***/ },
/* 760 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	module.exports = __webpack_require__(735);


/***/ },
/* 761 */
[1261, 589],
/* 762 */
[1282, 747],
/* 763 */
583,
/* 764 */
574,
/* 765 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(766);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../../css-loader/index.js!./jquery.fancybox.css", function() {
				var newContent = require("!!./../../../css-loader/index.js!./jquery.fancybox.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 766 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, "/*! fancyBox v2.1.5 fancyapps.com | fancyapps.com/fancybox/#license */\n.fancybox-wrap, .fancybox-skin, .fancybox-outer, .fancybox-inner, .fancybox-image, .fancybox-wrap iframe, .fancybox-wrap object, .fancybox-nav, .fancybox-nav span, .fancybox-tmp {\n  padding: 0;\n  margin: 0;\n  border: 0;\n  outline: none;\n  vertical-align: top; }\n\n.fancybox-wrap {\n  position: absolute;\n  top: 0;\n  left: 0;\n  z-index: 8020; }\n\n.fancybox-skin {\n  position: relative;\n  background: #f9f9f9;\n  color: #444;\n  text-shadow: none;\n  -webkit-border-radius: 4px;\n  -moz-border-radius: 4px;\n  border-radius: 4px; }\n\n.fancybox-opened {\n  z-index: 8030; }\n\n.fancybox-opened .fancybox-skin {\n  -webkit-box-shadow: 0 10px 25px rgba(0, 0, 0, 0.5);\n  -moz-box-shadow: 0 10px 25px rgba(0, 0, 0, 0.5);\n  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.5); }\n\n.fancybox-outer, .fancybox-inner {\n  position: relative; }\n\n.fancybox-inner {\n  overflow: hidden; }\n\n.fancybox-type-iframe .fancybox-inner {\n  -webkit-overflow-scrolling: touch; }\n\n.fancybox-error {\n  color: #444;\n  font: 14px/20px \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n  margin: 0;\n  padding: 15px;\n  white-space: nowrap; }\n\n.fancybox-image, .fancybox-iframe {\n  display: block;\n  width: 100%;\n  height: 100%; }\n\n.fancybox-image {\n  max-width: 100%;\n  max-height: 100%; }\n\n#fancybox-loading, .fancybox-close, .fancybox-prev span, .fancybox-next span {\n  background-image: url(" + __webpack_require__(768) + "); }\n\n#fancybox-loading {\n  position: fixed;\n  top: 50%;\n  left: 50%;\n  margin-top: -22px;\n  margin-left: -22px;\n  background-position: 0 -108px;\n  opacity: 0.8;\n  cursor: pointer;\n  z-index: 8060; }\n\n#fancybox-loading div {\n  width: 44px;\n  height: 44px;\n  background: url(" + __webpack_require__(769) + ") center center no-repeat; }\n\n.fancybox-close {\n  position: absolute;\n  top: -18px;\n  right: -18px;\n  width: 36px;\n  height: 36px;\n  cursor: pointer;\n  z-index: 8040; }\n\n.fancybox-nav {\n  position: absolute;\n  top: 0;\n  width: 40%;\n  height: 100%;\n  cursor: pointer;\n  text-decoration: none;\n  background: transparent url(" + __webpack_require__(770) + ");\n  /* helps IE */\n  -webkit-tap-highlight-color: transparent;\n  z-index: 8040; }\n\n.fancybox-prev {\n  left: 0; }\n\n.fancybox-next {\n  right: 0; }\n\n.fancybox-nav span {\n  position: absolute;\n  top: 50%;\n  width: 36px;\n  height: 34px;\n  margin-top: -18px;\n  cursor: pointer;\n  z-index: 8040;\n  visibility: hidden; }\n\n.fancybox-prev span {\n  left: 10px;\n  background-position: 0 -36px; }\n\n.fancybox-next span {\n  right: 10px;\n  background-position: 0 -72px; }\n\n.fancybox-nav:hover span {\n  visibility: visible; }\n\n.fancybox-tmp {\n  position: absolute;\n  top: -99999px;\n  left: -99999px;\n  max-width: 99999px;\n  max-height: 99999px;\n  overflow: visible !important; }\n\n/* Overlay helper */\n.fancybox-lock {\n  overflow: visible !important;\n  width: auto; }\n\n.fancybox-lock body {\n  overflow: hidden !important; }\n\n.fancybox-lock-test {\n  overflow-y: hidden !important; }\n\n.fancybox-overlay {\n  position: absolute;\n  top: 0;\n  left: 0;\n  overflow: hidden;\n  display: none;\n  z-index: 8010;\n  background: url(" + __webpack_require__(771) + "); }\n\n.fancybox-overlay-fixed {\n  position: fixed;\n  bottom: 0;\n  right: 0; }\n\n.fancybox-lock .fancybox-overlay {\n  overflow: auto;\n  overflow-y: scroll; }\n\n/* Title helper */\n.fancybox-title {\n  visibility: hidden;\n  font: normal 13px/20px \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n  position: relative;\n  text-shadow: none;\n  z-index: 8050; }\n\n.fancybox-opened .fancybox-title {\n  visibility: visible; }\n\n.fancybox-title-float-wrap {\n  position: absolute;\n  bottom: 0;\n  right: 50%;\n  margin-bottom: -35px;\n  z-index: 8050;\n  text-align: center; }\n\n.fancybox-title-float-wrap .child {\n  display: inline-block;\n  margin-right: -100%;\n  padding: 2px 20px;\n  background: transparent;\n  /* Fallback for web browsers that doesn't support RGBa */\n  background: rgba(0, 0, 0, 0.8);\n  -webkit-border-radius: 15px;\n  -moz-border-radius: 15px;\n  border-radius: 15px;\n  text-shadow: 0 1px 2px #222;\n  color: #FFF;\n  font-weight: bold;\n  line-height: 24px;\n  white-space: nowrap; }\n\n.fancybox-title-outside-wrap {\n  position: relative;\n  margin-top: 10px;\n  color: #fff; }\n\n.fancybox-title-inside-wrap {\n  padding-top: 10px; }\n\n.fancybox-title-over-wrap {\n  position: absolute;\n  bottom: 0;\n  left: 0;\n  color: #fff;\n  padding: 10px;\n  background: #000;\n  background: rgba(0, 0, 0, 0.8); }\n\n/*Retina graphics!*/\n@media only screen and (-webkit-min-device-pixel-ratio: 1.5), only screen and (min--moz-device-pixel-ratio: 1.5), only screen and (min-device-pixel-ratio: 1.5) {\n  #fancybox-loading, .fancybox-close, .fancybox-prev span, .fancybox-next span {\n    background-image: url(" + __webpack_require__(772) + ");\n    background-size: 44px 152px;\n    /*The size of the normal image, half the size of the hi-res image*/ }\n  #fancybox-loading div {\n    background-image: url(" + __webpack_require__(773) + ");\n    background-size: 24px 24px;\n    /*The size of the normal image, half the size of the hi-res image*/ } }\n", ""]);

	// exports


/***/ },
/* 767 */
/***/ function(module, exports) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	// css base code, injected by the css-loader
	module.exports = function() {
		var list = [];

		// return the list of modules as css string
		list.toString = function toString() {
			var result = [];
			for(var i = 0; i < this.length; i++) {
				var item = this[i];
				if(item[2]) {
					result.push("@media " + item[2] + "{" + item[1] + "}");
				} else {
					result.push(item[1]);
				}
			}
			return result.join("");
		};

		// import a list of modules into the list
		list.i = function(modules, mediaQuery) {
			if(typeof modules === "string")
				modules = [[null, modules, ""]];
			var alreadyImportedModules = {};
			for(var i = 0; i < this.length; i++) {
				var id = this[i][0];
				if(typeof id === "number")
					alreadyImportedModules[id] = true;
			}
			for(i = 0; i < modules.length; i++) {
				var item = modules[i];
				// skip already imported module
				// this implementation is not 100% perfect for weird media query combinations
				//  when a module is imported multiple times with different media queries.
				//  I hope this will never occur (Hey this way we have smaller bundles)
				if(typeof item[0] !== "number" || !alreadyImportedModules[item[0]]) {
					if(mediaQuery && !item[2]) {
						item[2] = mediaQuery;
					} else if(mediaQuery) {
						item[2] = "(" + item[2] + ") and (" + mediaQuery + ")";
					}
					list.push(item);
				}
			}
		};
		return list;
	};


/***/ },
/* 768 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "783d4031fe50c3d83c960911e1fbc705.png";

/***/ },
/* 769 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "e14dca13d1d24c7cdf89f8c7b20d57dc.gif";

/***/ },
/* 770 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "325472601571f31e1bf00674c368d335.gif";

/***/ },
/* 771 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "cbc2440151b716f07d1c90c7f3ed646c.png";

/***/ },
/* 772 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "4ac188774675ee61485cfa8cd5b8ca8d.png";

/***/ },
/* 773 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "5bafaeb221caf96cf68f94654d2e19a7.gif";

/***/ },
/* 774 */
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
		if(false) {
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
		var sourceMap = obj.sourceMap;

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
		var media = obj.media;
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


/***/ },
/* 775 */
[1285, 747],
/* 776 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(777);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../css-loader/index.js!./jquery.toolbar.css", function() {
				var newContent = require("!!./../css-loader/index.js!./jquery.toolbar.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 777 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".btn-toolbar {\n  background: #364347;\n  width: 20px;\n  height: 20px;\n  text-align: center;\n  padding: 10px;\n  border-radius: 6px;\n  display: block;\n  transition: none;\n}\n.btn-toolbar > i {\n  color: #02baf2;\n  font-size: 16px;\n}\n.btn-toolbar:hover {\n  background: #02baf2;\n  cursor: pointer;\n}\n.btn-toolbar:hover > i {\n  color: white;\n}\n.btn-toolbar-primary {\n  background-color: #009dcd;\n}\n.btn-toolbar-primary.pressed {\n  background-color: #02baf2;\n}\n.btn-toolbar-primary:hover {\n  background-color: #02baf2;\n}\n.btn-toolbar-primary > i {\n  color: white;\n}\n.btn-toolbar-danger {\n  background-color: #cc0000;\n}\n.btn-toolbar-danger.pressed {\n  background-color: #f84545;\n}\n.btn-toolbar-danger:hover {\n  background-color: #f84545;\n}\n.btn-toolbar-danger > i {\n  color: white;\n}\n.btn-toolbar-warning {\n  background-color: #f3bc65;\n}\n.btn-toolbar-warning.pressed {\n  background-color: #fad46b;\n}\n.btn-toolbar-warning:hover {\n  background-color: #fad46b;\n}\n.btn-toolbar-warning > i {\n  color: white;\n}\n.btn-toolbar-info {\n  background-color: #e96300;\n}\n.btn-toolbar-info.pressed {\n  background-color: #f58410;\n}\n.btn-toolbar-info:hover {\n  background-color: #f58410;\n}\n.btn-toolbar-info > i {\n  color: white;\n}\n.btn-toolbar-success {\n  background-color: #28948c;\n}\n.btn-toolbar-success.pressed {\n  background-color: #3eb5ac;\n}\n.btn-toolbar-success:hover {\n  background-color: #3eb5ac;\n}\n.btn-toolbar-success > i {\n  color: white;\n}\n.btn-toolbar-info-o {\n  background-color: #9175bd;\n}\n.btn-toolbar-info-o.pressed {\n  background-color: #a88cd5;\n}\n.btn-toolbar-info-o:hover {\n  background-color: #a88cd5;\n}\n.btn-toolbar-info-o > i {\n  color: white;\n}\n.btn-toolbar-light {\n  background-color: #b2c6cd;\n}\n.btn-toolbar-light.pressed {\n  background-color: #d6e1e5;\n}\n.btn-toolbar-light:hover {\n  background-color: #d6e1e5;\n}\n.btn-toolbar-light > i {\n  color: white;\n}\n.btn-toolbar-dark {\n  background-color: #364347;\n}\n.btn-toolbar-dark.pressed {\n  background-color: #5e696d;\n}\n.btn-toolbar-dark:hover {\n  background-color: #5e696d;\n}\n.btn-toolbar-dark > i {\n  color: white;\n}\n.tool-container {\n  background-color: #5e696d;\n  background-size: 100% 100%;\n  border-radius: 6px;\n  position: absolute;\n}\n.tool-container.tool-top,\n.tool-container.tool-bottom {\n  height: 40px;\n  border-bottom: 0px solid #beb8b8;\n}\n.tool-container.tool-top .tool-item,\n.tool-container.tool-bottom .tool-item {\n  float: left;\n  border-right: 0;\n  border-left: 0;\n}\n.tool-item {\n  height: 100%;\n  display: block;\n  width: 20px;\n  height: 20px;\n  text-align: center;\n  padding: 10px;\n  transition: none;\n}\n.tool-item > .fa {\n  color: #b2c6cd;\n}\n.tool-item.selected,\n.tool-item:hover {\n  background: #02baf2;\n}\n.tool-item.selected > .fa,\n.tool-item:hover > .fa {\n  color: white;\n}\n.tool-top .tool-item:first-child:hover,\n.tool-bottom .tool-item:first-child:hover {\n  border-top-left-radius: 6px;\n  border-bottom-left-radius: 6px;\n}\n.tool-top .tool-item:last-child:hover,\n.tool-bottom .tool-item:last-child:hover {\n  border-top-right-radius: 6px;\n  border-bottom-right-radius: 6px;\n}\n.tool-vertical-top .tool-item:first-child:hover,\n.tool-vertical-bottom .tool-item:first-child:hover,\n.tool-right .tool-item:first-child:hover,\n.tool-left .tool-item:first-child:hover {\n  border-top-left-radius: 6px;\n  border-top-right-radius: 6px;\n}\n.tool-vertical-top .tool-item:last-child:hover,\n.tool-vertical-bottom .tool-item:last-child:hover,\n.tool-right .tool-item:last-child:hover,\n.tool-left .tool-item:last-child:hover {\n  border-bottom-left-radius: 6px;\n  border-bottom-right-radius: 6px;\n}\n.tool-container .arrow {\n  width: 0;\n  height: 0;\n  position: absolute;\n  border-width: 7px;\n  border-style: solid;\n}\n.tool-container.tool-top .arrow {\n  border-color: #5e696d transparent transparent;\n  left: 50%;\n  bottom: -14px;\n  margin-left: -7px;\n}\n.tool-container.tool-bottom .arrow {\n  border-color: transparent transparent #5e696d;\n  left: 50%;\n  top: -14px;\n  margin-left: -7px;\n}\n.tool-container.tool-left .arrow {\n  border-color: transparent transparent transparent #5e696d;\n  top: 50%;\n  right: -14px;\n  margin-top: -7px;\n}\n.tool-container.tool-right .arrow {\n  border-color: transparent #5e696d transparent transparent;\n  top: 50%;\n  left: -14px;\n  margin-top: -7px;\n}\n.toolbar-primary {\n  background-color: #02baf2;\n}\n.toolbar-primary.tool-top .arrow {\n  border-color: #02baf2 transparent transparent;\n}\n.toolbar-primary.tool-bottom .arrow {\n  border-color: transparent transparent #02baf2;\n}\n.toolbar-primary.tool-left .arrow {\n  border-color: transparent transparent transparent #02baf2;\n}\n.toolbar-primary.tool-right .arrow {\n  border-color: transparent #02baf2 transparent transparent;\n}\n.toolbar-primary .tool-item > .fa {\n  color: white;\n}\n.toolbar-primary .tool-item.selected,\n.toolbar-primary .tool-item:hover {\n  background: #009dcd;\n  color: white;\n}\n.toolbar-danger {\n  background-color: #f84545;\n}\n.toolbar-danger.tool-top .arrow {\n  border-color: #f84545 transparent transparent;\n}\n.toolbar-danger.tool-bottom .arrow {\n  border-color: transparent transparent #f84545;\n}\n.toolbar-danger.tool-left .arrow {\n  border-color: transparent transparent transparent #f84545;\n}\n.toolbar-danger.tool-right .arrow {\n  border-color: transparent #f84545 transparent transparent;\n}\n.toolbar-danger .tool-item > .fa {\n  color: white;\n}\n.toolbar-danger .tool-item.selected,\n.toolbar-danger .tool-item:hover {\n  background: #cc0000;\n  color: white;\n}\n.toolbar-warning {\n  background-color: #f3bc65;\n}\n.toolbar-warning.tool-top .arrow {\n  border-color: #f3bc65 transparent transparent;\n}\n.toolbar-warning.tool-bottom .arrow {\n  border-color: transparent transparent #f3bc65;\n}\n.toolbar-warning.tool-left .arrow {\n  border-color: transparent transparent transparent #f3bc65;\n}\n.toolbar-warning.tool-right .arrow {\n  border-color: transparent #f3bc65 transparent transparent;\n}\n.toolbar-warning .tool-item > .fa {\n  color: white;\n}\n.toolbar-warning .tool-item.selected,\n.toolbar-warning .tool-item:hover {\n  background: #fad46b;\n  color: white;\n}\n.toolbar-info {\n  background-color: #e96300;\n}\n.toolbar-info.tool-top .arrow {\n  border-color: #e96300 transparent transparent;\n}\n.toolbar-info.tool-bottom .arrow {\n  border-color: transparent transparent #e96300;\n}\n.toolbar-info.tool-left .arrow {\n  border-color: transparent transparent transparent #e96300;\n}\n.toolbar-info.tool-right .arrow {\n  border-color: transparent #e96300 transparent transparent;\n}\n.toolbar-info .tool-item > .fa {\n  color: white;\n}\n.toolbar-info .tool-item.selected,\n.toolbar-info .tool-item:hover {\n  background: #f58410;\n  color: white;\n}\n.toolbar-success {\n  background-color: #28948c;\n}\n.toolbar-success.tool-top .arrow {\n  border-color: #28948c transparent transparent;\n}\n.toolbar-success.tool-bottom .arrow {\n  border-color: transparent transparent #28948c;\n}\n.toolbar-success.tool-left .arrow {\n  border-color: transparent transparent transparent #28948c;\n}\n.toolbar-success.tool-right .arrow {\n  border-color: transparent #28948c transparent transparent;\n}\n.toolbar-success .tool-item > .fa {\n  color: white;\n}\n.toolbar-success .tool-item.selected,\n.toolbar-success .tool-item:hover {\n  background: #3eb5ac;\n  color: white;\n}\n.toolbar-info-o {\n  background-color: #9175bd;\n}\n.toolbar-info-o.tool-top .arrow {\n  border-color: #9175bd transparent transparent;\n}\n.toolbar-info-o.tool-bottom .arrow {\n  border-color: transparent transparent #9175bd;\n}\n.toolbar-info-o.tool-left .arrow {\n  border-color: transparent transparent transparent #9175bd;\n}\n.toolbar-info-o.tool-right .arrow {\n  border-color: transparent #9175bd transparent transparent;\n}\n.toolbar-info-o .tool-item > .fa {\n  color: white;\n}\n.toolbar-info-o .tool-item.selected,\n.toolbar-info-o .tool-item:hover {\n  background: #a88cd5;\n  color: white;\n}\n.toolbar-light {\n  background-color: #b2c6cd;\n}\n.toolbar-light.tool-top .arrow {\n  border-color: #b2c6cd transparent transparent;\n}\n.toolbar-light.tool-bottom .arrow {\n  border-color: transparent transparent #b2c6cd;\n}\n.toolbar-light.tool-left .arrow {\n  border-color: transparent transparent transparent #b2c6cd;\n}\n.toolbar-light.tool-right .arrow {\n  border-color: transparent #b2c6cd transparent transparent;\n}\n.toolbar-light .tool-item > .fa {\n  color: white;\n}\n.toolbar-light .tool-item.selected,\n.toolbar-light .tool-item:hover {\n  background: #d6e1e5;\n  color: white;\n}\n.toolbar-dark {\n  background-color: #364347;\n}\n.toolbar-dark.tool-top .arrow {\n  border-color: #364347 transparent transparent;\n}\n.toolbar-dark.tool-bottom .arrow {\n  border-color: transparent transparent #364347;\n}\n.toolbar-dark.tool-left .arrow {\n  border-color: transparent transparent transparent #364347;\n}\n.toolbar-dark.tool-right .arrow {\n  border-color: transparent #364347 transparent transparent;\n}\n.toolbar-dark .tool-item > .fa {\n  color: white;\n}\n.toolbar-dark .tool-item.selected,\n.toolbar-dark .tool-item:hover {\n  background: #5e696d;\n  color: white;\n}\n.animate-standard {\n  -webkit-animation: standardAnimate 0.3s 1 ease;\n}\n.animate-flyin {\n  -webkit-animation: rotateAnimate 0.5s 1 ease;\n}\n.animate-grow {\n  -webkit-animation: growAnimate 0.4s 1 ease;\n}\n.animate-flip {\n  -webkit-animation: flipAnimate 0.4s 1 ease;\n}\n.animate-bounce {\n  -webkit-animation: bounceAnimate 0.4s 1 ease-out;\n}\n@-webkit-keyframes rotateAnimate {\n  from {\n    transform: rotate(180deg) translate(-120px);\n    opacity: 0;\n  }\n  to {\n    transform: rotate(0deg) translate(0px);\n    opacity: 1;\n  }\n}\n@-webkit-keyframes standardAnimate {\n  from {\n    transform: translateY(20px);\n    opacity: 0;\n  }\n  to {\n    transform: translateY(0px);\n    opacity: 1;\n  }\n}\n@-webkit-keyframes growAnimate {\n  0% {\n    transform: scale(0) translateY(40px);\n    opacity: 0;\n  }\n  70% {\n    transform: scale(1.5) translate(0px);\n  }\n  100% {\n    transform: scale(1) translate(0px);\n    opacity: 1;\n  }\n}\n@-webkit-keyframes rotate2Animate {\n  from {\n    transform: rotate(-90deg);\n    transform-origin: 0% 100%;\n    opacity: 0;\n  }\n  to {\n    transform: rotate(0deg);\n    opacity: 1;\n  }\n}\n@-webkit-keyframes flipAnimate {\n  from {\n    transform: rotate3d(2, 2, 2, 180deg);\n    opacity: 0;\n  }\n  to {\n    transform: rotate3d(0, 0, 0, 0deg);\n    opacity: 1;\n  }\n}\n@-webkit-keyframes bounceAnimate {\n  0% {\n    transform: translateY(40px);\n    opacity: 0;\n  }\n  30% {\n    transform: translateY(-40px);\n  }\n  70% {\n    transform: translateY(20px);\n  }\n  100% {\n    transform: translateY(0px);\n    opacity: 1;\n  }\n}\n.hidden {\n  display: none;\n}\n", ""]);

	// exports


/***/ },
/* 778 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(779);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./jquery-toolbar-white.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./jquery-toolbar-white.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 779 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, "/* Custom style for jquery-toolbar */\n\n.tool-container {\n    box-shadow: 0 0 2px grey;\n}\n\n.btn-toolbar-white {\n    background-color: #b2c6cd;\n}\n.btn-toolbar-white.pressed {\n    background-color: #d6e1e5;\n}\n.btn-toolbar-white:hover {\n    background-color: #d6e1e5;\n}\n.btn-toolbar-white > i {\n    color: white;\n}\n\n.toolbar-white {\n    background-color: whitesmoke;\n}\n.toolbar-white.tool-top .arrow {\n    border-color: #b2c6cd transparent transparent;\n}\n.toolbar-white.tool-bottom .arrow {\n    border-color: transparent transparent #b2c6cd;\n}\n.toolbar-white.tool-left .arrow {\n    border-color: transparent transparent transparent #b2c6cd;\n}\n.toolbar-white.tool-right .arrow {\n    border-color: transparent lightgrey transparent transparent;\n}\n.toolbar-white .tool-item > .fa {\n    color: white;\n}\n.toolbar-white .tool-item.selected,\n.toolbar-white .tool-item:hover {\n    background: lightgoldenrodyellow;\n    color: white;\n}\n", ""]);

	// exports


/***/ },
/* 780 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(781);


/***/ },
/* 781 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var Highcharts = __webpack_require__(782);
	__webpack_require__(784)(Highcharts.Highcharts);

	//*------------------------------------------------------------------*

	var HeatmapBaselineCellVariance = React.createClass({
	    displayName: 'HeatmapBaselineCellVariance',


	    propTypes: {
	        quartiles: React.PropTypes.shape({
	            min: React.PropTypes.number,
	            lower: React.PropTypes.number,
	            median: React.PropTypes.number,
	            upper: React.PropTypes.number,
	            max: React.PropTypes.number
	        }).isRequired
	    },

	    render: function () {

	        var chartWidth = 115;
	        var chartHeight = 105;
	        var chartMargin = 0;

	        var highchartsOptions = {
	            credits: { enabled: false },
	            chart: { type: "boxplot", width: chartWidth, height: chartHeight, margin: chartMargin },
	            title: { text: "" },
	            legend: { enabled: false },
	            xAxis: { title: { text: "Variance" } },
	            yAxis: {
	                title: { text: "Expression level" },
	                labels: {
	                    align: "left",
	                    x: 0,
	                    y: -2
	                }
	            },
	            plotOptions: {
	                boxplot: {
	                    fillColor: "#F0F0E0",
	                    lineWidth: 2,
	                    medianColor: "#0C5DA5",
	                    medianWidth: 3,
	                    stemColor: "#A63400",
	                    stemDashStyle: "dot",
	                    stemWidth: 1,
	                    whiskerColor: "#3D9200",
	                    whiskerLength: "20%",
	                    whiskerWidth: 3
	                }
	            },
	            series: [{
	                name: "Expression",
	                data: [[this.props.quartiles.min, this.props.quartiles.lower, this.props.quartiles.median, this.props.quartiles.upper, this.props.quartiles.max]]
	            }],
	            tooltip: {
	                headerFormat: "",
	                style: {
	                    fontSize: "10px",
	                    padding: 5
	                }
	            }
	        };

	        var boxPlotStyle = { width: chartWidth, height: chartHeight, margin: chartMargin };
	        return React.createElement(
	            'td',
	            null,
	            React.createElement(
	                'div',
	                { id: 'container', ref: 'container', style: boxPlotStyle },
	                React.createElement(Highcharts, { config: highchartsOptions })
	            )
	        );
	    }

	});

	//*------------------------------------------------------------------*

	module.exports = HeatmapBaselineCellVariance;

/***/ },
/* 782 */
[1287, 589, 783],
/* 783 */
587,
/* 784 */
585,
/* 785 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	exports.LegendDifferential = __webpack_require__(786);
	exports.LegendBaseline = __webpack_require__(797);

/***/ },
/* 786 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);

	//*------------------------------------------------------------------*

	var LegendRow = __webpack_require__(787);
	var HelpTooltips = __webpack_require__(790);

	//*------------------------------------------------------------------*

	__webpack_require__(795);

	//*------------------------------------------------------------------*

	var LegendDifferential = React.createClass({
	    displayName: 'LegendDifferential',


	    propTypes: {
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        minDownLevel: React.PropTypes.string.isRequired,
	        maxDownLevel: React.PropTypes.string.isRequired,
	        minUpLevel: React.PropTypes.string.isRequired,
	        maxUpLevel: React.PropTypes.string.isRequired
	    },

	    render: function () {
	        return React.createElement(
	            'div',
	            { className: 'gxaLegend' },
	            React.createElement(
	                'div',
	                { style: { display: "inline-table" } },
	                isNaN(this.props.minDownLevel) && isNaN(this.props.maxDownLevel) ? null : React.createElement(LegendRow, { lowExpressionLevel: this.props.minDownLevel,
	                    highExpressionLevel: this.props.maxDownLevel,
	                    lowValueColour: '#C0C0C0',
	                    highValueColour: '#0000FF' }),
	                isNaN(this.props.minUpLevel) && isNaN(this.props.maxUpLevel) ? null : React.createElement(LegendRow, { lowExpressionLevel: this.props.minUpLevel,
	                    highExpressionLevel: this.props.maxUpLevel,
	                    lowValueColour: '#FFAFAF',
	                    highValueColour: '#FF0000' })
	            ),
	            React.createElement('div', { ref: 'legendHelp', 'data-help-loc': '#gradient-differential', className: 'gxaLegendHelp' })
	        );
	    },

	    componentDidMount: function () {
	        HelpTooltips(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.refs.legendHelp));
	    }
	});

	//*------------------------------------------------------------------*

	module.exports = LegendDifferential;

/***/ },
/* 787 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);

	//*------------------------------------------------------------------*

	__webpack_require__(788);

	//*------------------------------------------------------------------*

	var LegendRow = React.createClass({
	    displayName: 'LegendRow',


	    propTypes: {
	        lowValueColour: React.PropTypes.string.isRequired,
	        highValueColour: React.PropTypes.string.isRequired,
	        lowExpressionLevel: React.PropTypes.oneOfType([React.PropTypes.string, React.PropTypes.element]).isRequired, // Baseline legend rows can be a React <span> element returned by NumberFormat
	        highExpressionLevel: React.PropTypes.oneOfType([React.PropTypes.string, React.PropTypes.element]).isRequired
	    },

	    render: function () {
	        var BACKGROUND_IMAGE_TEMPLATE = "-webkit-gradient(linear, left top, right top,color-stop(0, ${lowValueColour}), color-stop(1, ${highValueColour}));background-image: -moz-linear-gradient(left, ${lowValueColour}, ${highValueColour});background-image: -ms-linear-gradient(left, ${lowValueColour}, ${highValueColour}); background-image: -o-linear-gradient(left, ${lowValueColour}, ${highValueColour})";
	        var backgroundImage = BACKGROUND_IMAGE_TEMPLATE.replace(/\${lowValueColour}/g, this.props.lowValueColour).replace(/\${highValueColour}/g, this.props.highValueColour);

	        // for IE9
	        var LT_IE10_FILTER_TEMPLATE = "progid:DXImageTransform.Microsoft.Gradient(GradientType =1,startColorstr=${lowValueColour},endColorstr=${highValueColour})";
	        var lt_ie10_filter = LT_IE10_FILTER_TEMPLATE.replace(/\${lowValueColour}/, this.props.lowValueColour).replace(/\${highValueColour}/, this.props.highValueColour);

	        return React.createElement(
	            'div',
	            { style: { display: "table-row" } },
	            React.createElement(
	                'div',
	                { className: 'gxaGradientLevel gxaGradientLevelMin' },
	                this.props.lowExpressionLevel
	            ),
	            React.createElement(
	                'div',
	                { style: { display: "table-cell" } },
	                React.createElement('span', { className: 'gxaGradientColour', style: { backgroundImage: backgroundImage, filter: lt_ie10_filter } })
	            ),
	            React.createElement(
	                'div',
	                { className: 'gxaGradientLevel gxaGradientLevelMax' },
	                this.props.highExpressionLevel
	            )
	        );
	    }
	});

	//*------------------------------------------------------------------*

	module.exports = LegendRow;

/***/ },
/* 788 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(789);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaGradient.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaGradient.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 789 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaGradientColour {\n    overflow: auto;\n    vertical-align: middle;\n    width: 200px;\n    height: 15px;\n    margin: 2px 6px 2px 6px;\n    display: inline-block;\n}\n\n.gxaGradientLevel {\n    white-space: nowrap;\n    font-size: 10px;\n    vertical-align: middle;\n    display: table-cell;\n}\n\n.gxaGradientLevelMin {\n    text-align: right;\n}\n\n.gxaGradientLevelMax {\n    text-align: left;\n}", ""]);

	// exports


/***/ },
/* 790 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(791);


/***/ },
/* 791 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var $ = __webpack_require__(747);
	__webpack_require__(757);
	__webpack_require__(792);

	//*------------------------------------------------------------------*

	__webpack_require__(793);

	//*------------------------------------------------------------------*

	function buildHelpAnchor() {
	    return $("<a/>", {
	        class: "help-icon",
	        href: "#",
	        title: "",
	        text: "?"
	    });
	}

	function getHelpFileName(pageName){
	    return "help-tooltips." + pageName + "-page.html";
	}

	function initTooltips(atlasBaseURL, pageName, parentElementId) {

	    var anchor = buildHelpAnchor();

	    var helpSelector = (typeof parentElementId === "object") ? parentElementId : (parentElementId == "") ? "[data-help-loc]" : "#" + parentElementId + " [data-help-loc]";

	    $(helpSelector)
	        .append(anchor)
	        .click(function (e) {
	            e.preventDefault();
	        })
	        .tooltip(
	        {
	            tooltipClass: "gxaHelpTooltip",
	            content: function (callback) {
	                var tooltipHelpHtmlId = $(this).parent().attr("data-help-loc");

	                $.get(atlasBaseURL + "/resources/html/" + getHelpFileName(pageName),
	                    function (response, status, xhr) {
	                        var tooltipContent;

	                        if (status === "error") {
	                            tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
	                            callback(tooltipContent);
	                            return;
	                        }

	                        tooltipContent = $(response).filter(tooltipHelpHtmlId).text();
	                        if (!tooltipContent) {
	                            tooltipContent = "Missing help section for id = " + tooltipHelpHtmlId + " in html file " + getHelpFileName(pageName);
	                        }

	                        callback(tooltipContent);
	                    }
	                );
	            }
	        }
	    );

	}

	//*------------------------------------------------------------------*

	module.exports = function (atlasBaseURL, pageName, parentElementId) {
	    initTooltips(atlasBaseURL, pageName, parentElementId);
	};

/***/ },
/* 792 */
[1283, 747],
/* 793 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(794);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaHelpTooltip.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaHelpTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 794 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaHelpTooltip {\n    background: white;\n    border-width: 1px !important;\n    border: solid cornflowerblue;\n    padding: 4px;\n    color: cornflowerblue;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\na.help-icon {\n    color: darkorange;\n    vertical-align: top;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    font-weight: bold;\n}\n", ""]);

	// exports


/***/ },
/* 795 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(796);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaLegend.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaLegend.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 796 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaLegendHelp {\n    display: inline-block;\n    vertical-align: top;\n    padding-left: 2px;\n}\n\n.gxaLegend {\n    display: inline-block;\n    padding-left: 20px;\n}\n", ""]);

	// exports


/***/ },
/* 797 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);

	//*------------------------------------------------------------------*

	var LegendRow = __webpack_require__(787);
	var NumberFormat = __webpack_require__(798);
	var HelpTooltips = __webpack_require__(790);

	//*------------------------------------------------------------------*

	__webpack_require__(795);

	//*------------------------------------------------------------------*

	var LegendBaseline = React.createClass({
	    displayName: 'LegendBaseline',


	    propTypes: {
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        minExpressionLevel: React.PropTypes.string.isRequired,
	        maxExpressionLevel: React.PropTypes.string.isRequired,
	        isMultiExperiment: React.PropTypes.bool.isRequired
	    },

	    render: function () {
	        var dataHelpLoc = this.props.isMultiExperiment ? "#gradient-base-crossexp" : "#gradient-base";

	        // The class gxaHeatmapLegendGradient is used for Selenium tests but isn’t styled
	        return React.createElement(
	            'div',
	            { className: 'gxaHeatmapLegendGradient' },
	            React.createElement(
	                'div',
	                { style: { display: "inline-table" } },
	                React.createElement(LegendRow, { lowExpressionLevel: NumberFormat.baselineExpression(this.props.minExpressionLevel),
	                    highExpressionLevel: NumberFormat.baselineExpression(this.props.maxExpressionLevel),
	                    lowValueColour: '#C0C0C0',
	                    highValueColour: '#0000FF' })
	            ),
	            React.createElement('div', { ref: 'legendHelp', 'data-help-loc': dataHelpLoc, className: 'gxaLegendHelp' })
	        );
	    },

	    componentDidMount: function () {
	        HelpTooltips(this.props.atlasBaseURL, "experiment", ReactDOM.findDOMNode(this.refs.legendHelp));
	    }
	});

	//*------------------------------------------------------------------*

	module.exports = LegendBaseline;

/***/ },
/* 798 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(799);


/***/ },
/* 799 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	var React = __webpack_require__(589); // React is called in the transpiled JS files in the return statements

	//*------------------------------------------------------------------*

	function formatBaselineExpression(expressionLevel) {
	    var numberExpressionLevel = +expressionLevel;
	    return numberExpressionLevel >= 100000 || numberExpressionLevel < 0.1 ? formatScientificNotation(numberExpressionLevel.toExponential(1).replace('+', '')) : '' + numberExpressionLevel;
	}

	// expects number in the format #E# and displays exponent in superscript
	function formatScientificNotation(scientificNotationString) {

	    var formatParts = scientificNotationString.split(/[Ee]/);

	    if (formatParts.length == 1) {
	        return React.createElement(
	            'span',
	            null,
	            scientificNotationString
	        );
	    }

	    var mantissa = formatParts[0];
	    var exponent = formatParts[1];

	    return React.createElement(
	        'span',
	        null,
	        mantissa !== "1" ? mantissa + " \u00D7 " : '',
	        '10',
	        React.createElement(
	            'span',
	            { style: { 'verticalAlign': 'super' } },
	            exponent
	        )
	    );
	}

	//*------------------------------------------------------------------*

	exports.baselineExpression = formatBaselineExpression;
	exports.scientificNotation = formatScientificNotation;

/***/ },
/* 800 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(801);


/***/ },
/* 801 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);
	var ReactDOMServer = __webpack_require__(760);
	var $ = __webpack_require__(747);
	__webpack_require__(757);

	//*------------------------------------------------------------------*

	var NumberFormat = __webpack_require__(798);

	//*------------------------------------------------------------------*

	__webpack_require__(802);
	__webpack_require__(804);

	//*------------------------------------------------------------------*

	var CellDifferential = React.createClass({
	    displayName: 'CellDifferential',


	    propTypes: {
	        fontSize: React.PropTypes.number,
	        colour: React.PropTypes.string,
	        foldChange: React.PropTypes.string,
	        pValue: React.PropTypes.string,
	        tStat: React.PropTypes.string,
	        displayLevels: React.PropTypes.bool.isRequired
	    },

	    _hasValue: function () {
	        return this.props.foldChange !== undefined;
	    },

	    _getStyle: function () {
	        var style = {};
	        if (this.props.fontSize) {
	            style.fontSize = this.props.fontSize + "px";
	        }

	        return style;
	    },

	    render: function () {
	        if (!this._hasValue()) {
	            return React.createElement('td', null);
	        }

	        return React.createElement(
	            'td',
	            { style: { backgroundColor: this.props.colour, verticalAlign: "middle" } },
	            React.createElement(
	                'div',
	                { style: this._getStyle(), className: this.props.displayLevels ? "gxaShowCell" : "gxaHideCell" },
	                this.props.foldChange
	            )
	        );
	    },

	    componentDidMount: function () {
	        if (this._hasValue()) {
	            this._initTooltip(ReactDOM.findDOMNode(this));
	        }
	    },

	    _initTooltip: function (element) {

	        //TODO - build this from a React component, like we do for FactorTooltip
	        function buildHeatmapCellTooltip(pValue, tStatistic, foldChange) {

	            return "<table>" + "<thead>" + (pValue !== undefined ? "<th>Adjusted <em>p</em>-value</th>" : "") + (tStatistic !== undefined ? "<th><em>t</em>-statistic</th>" : "") + "<th class='gxaHeaderCell'>Log<sub>2</sub>-fold change</th>" + "</thead>" + "<tbody>" + "<tr>" + (pValue !== undefined ? "<td>" + ReactDOMServer.renderToStaticMarkup(NumberFormat.scientificNotation(pValue)) + "</td>" : "") + (tStatistic !== undefined ? "<td>" + tStatistic + "</td>" : "") + "<td>" + foldChange + "</td>" + "</tr>" + "</tbody>" + "</table>";
	        }

	        // Don’t use bind, tooltip uses this internally
	        var thisProps = this.props;

	        $(element).attr("title", "").tooltip({
	            open: function (event, ui) {
	                ui.tooltip.css("background", thisProps.colour);
	            },

	            tooltipClass: "gxaDifferentialCellTooltip",

	            content: function () {
	                return buildHeatmapCellTooltip(thisProps.pValue, thisProps.tStat, thisProps.foldChange);
	            }
	        });
	    }
	});

	//*------------------------------------------------------------------*

	module.exports = CellDifferential;

/***/ },
/* 802 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(803);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaShowHideCell.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaShowHideCell.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 803 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaShowCell {\n    background-color: white;\n    white-space: nowrap;\n    text-align: center;\n    margin: 4px;\n    padding: 2px;\n    font-size: 9px;\n}\n\n.gxaHideCell {\n    display: none;\n    visibility: hidden;\n}\n", ""]);

	// exports


/***/ },
/* 804 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(805);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaDifferentialCellTooltip.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaDifferentialCellTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 805 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaDifferentialCellTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n}\n\n.gxaDifferentialCellTooltip table {\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaDifferentialCellTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaDifferentialCellTooltip td {\n    white-space: nowrap;\n}\n\n.gxaDifferentialCellTooltip td, .gxaDifferentialCellTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n    width: 25px;\n}\n", ""]);

	// exports


/***/ },
/* 806 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(807);


/***/ },
/* 807 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);

	var $ = __webpack_require__(747);
	__webpack_require__(757);

	//*------------------------------------------------------------------*

	//*------------------------------------------------------------------*

	var DisplayLevelsButton = React.createClass({
	    displayName: 'DisplayLevelsButton',


	    propTypes: {
	        hideText: React.PropTypes.string.isRequired,
	        showText: React.PropTypes.string.isRequired,
	        onClickCallback: React.PropTypes.func.isRequired,
	        displayLevels: React.PropTypes.bool.isRequired,
	        width: React.PropTypes.string,
	        fontSize: React.PropTypes.string
	    },

	    _buttonText: function () {
	        return this.props.displayLevels ? this.props.hideText : this.props.showText;
	    },

	    _updateButtonText: function () {
	        $(ReactDOM.findDOMNode(this)).button({ label: this._buttonText() });
	    },

	    render: function () {
	        var style = {
	            textAlign: "center"
	        };
	        if (this.props.width) {
	            style.width = this.props.width;
	        }
	        if (this.props.fontSize) {
	            style.fontSize = this.props.fontSize;
	        }

	        return React.createElement('button', { style: style, onClick: this.props.onClickCallback });
	    },

	    componentDidMount: function () {
	        this._updateButtonText();
	    },

	    componentDidUpdate: function () {
	        this._updateButtonText();
	    }

	});

	//*------------------------------------------------------------------*

	module.exports = DisplayLevelsButton;

/***/ },
/* 808 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = __webpack_require__(809);


/***/ },
/* 809 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOMServer = __webpack_require__(760);

	var $ = __webpack_require__(747);
	__webpack_require__(757);
	__webpack_require__(810);

	//*------------------------------------------------------------------*

	var ContrastTooltip = __webpack_require__(811);

	//*------------------------------------------------------------------*

	__webpack_require__(812);

	//*------------------------------------------------------------------*

	function initTooltip(contextRoot, accessKey, element, experimentAccession, contrastId) {

	    $(element).attr("title", "").tooltip({

	        hide: false,

	        show: false,

	        tooltipClass: "gxaContrastTooltip",

	        close: function() {
	            $(".gxaContrastTooltip").remove();
	        },

	        content: function (callback) {
	            $.ajax({
	                url:contextRoot + "/rest/contrast-summary",
	                data:{
	                    experimentAccession: experimentAccession,
	                    contrastId: contrastId,
	                    accessKey: accessKey
	                },
	                type:"GET",
	                success:function (data) {
	                    var html =
	                        ReactDOMServer.renderToString(
	                            React.createElement(
	                                ContrastTooltip,
	                                {
	                                    experimentDescription: data.experimentDescription,
	                                    contrastDescription: data.contrastDescription,
	                                    testReplicates: data.testReplicates,
	                                    referenceReplicates: data.referenceReplicates,
	                                    properties: data.properties
	                                }
	                            )
	                        );
	                    callback(html);
	                }
	            }).fail(function (data) {
	                console.log("ERROR:  " + data);
	                callback("ERROR: " + data);
	            });
	        }

	    });

	}

	//*------------------------------------------------------------------*

	module.exports = function (contextRoot, accessKey, element, experimentAccession, contrastId) {
	    initTooltip(contextRoot, accessKey, element, experimentAccession, contrastId);
	};



/***/ },
/* 810 */
[1283, 747],
/* 811 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);

	//*------------------------------------------------------------------*

	var ContrastTooltip = React.createClass({
	    displayName: "ContrastTooltip",

	    propTypes: {
	        experimentDescription: React.PropTypes.string.isRequired,
	        contrastDescription: React.PropTypes.string.isRequired,
	        testReplicates: React.PropTypes.number.isRequired,
	        referenceReplicates: React.PropTypes.number.isRequired,
	        properties: React.PropTypes.arrayOf(React.PropTypes.shape({
	            contrastPropertyType: React.PropTypes.string,
	            propertyName: React.PropTypes.string.isRequired,
	            referenceValue: React.PropTypes.string.isRequired,
	            testValue: React.PropTypes.string.isRequired
	        }))
	    },

	    propertyRow: function (property) {
	        if (!property.testValue && !property.referenceValue) {
	            return null;
	        }

	        function isFactor(property) {
	            return property.contrastPropertyType === "FACTOR";
	        }

	        var style = { whiteSpace: "normal" };

	        if (isFactor(property)) {
	            style.fontWeight = "bold";
	        } else {
	            style.color = "gray";
	        }

	        return React.createElement(
	            "tr",
	            { key: property.contrastPropertyType + "-" + property.propertyName },
	            React.createElement(
	                "td",
	                { style: style },
	                property.propertyName
	            ),
	            React.createElement(
	                "td",
	                { style: style },
	                property.testValue
	            ),
	            React.createElement(
	                "td",
	                { style: style },
	                property.referenceValue
	            )
	        );
	    },

	    render: function () {
	        return React.createElement(
	            "div",
	            null,
	            React.createElement(
	                "div",
	                { id: "contrastExperimentDescription", style: { fontWeight: "bold", color: "blue", textAlign: "center" } },
	                this.props.experimentDescription
	            ),
	            React.createElement(
	                "div",
	                { id: "contrastDescription", style: { textAlign: "center" } },
	                this.props.contrastDescription
	            ),
	            React.createElement(
	                "table",
	                { style: { padding: "0px", margin: "0px", width: "100%" } },
	                React.createElement(
	                    "thead",
	                    null,
	                    React.createElement(
	                        "tr",
	                        null,
	                        React.createElement(
	                            "th",
	                            null,
	                            "Property"
	                        ),
	                        React.createElement(
	                            "th",
	                            null,
	                            "Test value (N=",
	                            this.props.testReplicates,
	                            ")"
	                        ),
	                        React.createElement(
	                            "th",
	                            null,
	                            "Reference value (N=",
	                            this.props.referenceReplicates,
	                            ")"
	                        )
	                    )
	                ),
	                React.createElement(
	                    "tbody",
	                    null,
	                    this.props.properties.map(this.propertyRow)
	                )
	            )
	        );
	    }

	});

	//*------------------------------------------------------------------*

	module.exports = ContrastTooltip;

/***/ },
/* 812 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(813);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../../css-loader/index.js!./gxaContrastTooltip.css", function() {
				var newContent = require("!!./../../css-loader/index.js!./gxaContrastTooltip.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 813 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaContrastTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaContrastTooltip table {\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaContrastTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaContrastTooltip td {\n    border: 1px solid lightgrey;\n}\n\n.gxaContrastTooltip td, .gxaContrastTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n", ""]);

	// exports


/***/ },
/* 814 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var $ = __webpack_require__(747);
	__webpack_require__(757);

	//*------------------------------------------------------------------*

	__webpack_require__(815);

	//*------------------------------------------------------------------*

	/**
	 * @param {Object} options
	 * @param {string} options.contextRoot
	 * @param {string} options.geneName
	 * @param {string} options.identifier
	 * @param {Object} options.element
	 */
	function initTooltip(options){

	    $(options.element).attr("title", "").tooltip({

	        hide: false,

	        show: false,

	        tooltipClass: "gxaGeneNameTooltip",

	        close: function() {
	            $(".gxaGeneNameTooltip").remove();
	        },

	        position: { my: "left+10 top", at: "right"},

	        content: function (callback) {
	            if (options.identifier)  {
	                $.ajax({
	                    url: options.contextRoot + "/rest/genename-tooltip",
	                    data: {
	                        geneName: options.geneName,
	                        identifier: options.identifier
	                    },
	                    type:"GET",
	                    success: function (response) {
	                        if (!response) {
	                            callback("Missing properties for id = " + options.identifier + " in Solr.");
	                        }

	                        callback(response);
	                    }
	                }).fail(function (data) {
	                    console.log("ERROR:  " + data);
	                    callback("ERROR: " + data);
	                });
	            }
	        }

	    });

	}

	//*------------------------------------------------------------------*

	exports.init = function(contextRoot, element, identifier, geneName) {
	    initTooltip({contextRoot: contextRoot, element: element, identifier: identifier, geneName: geneName});
	};


/***/ },
/* 815 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(816);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./gene-properties-tooltip-module.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./gene-properties-tooltip-module.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 816 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaGeneNameTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    background: floralwhite;\n}\n\nspan.gxaGenePropertyLabel {\n    color: brown;\n    font-weight: bold;\n    display: inline-block;\n    text-align: left;\n}\n\n.gxaPropertyValueMarkup {\n    text-align: center;\n    background-color: rgb(223, 213, 213);\n\n}\n\n.gxaGeneNameTooltip {\n    text-align: justify;\n}", ""]);

	// exports


/***/ },
/* 817 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOMServer = __webpack_require__(760);

	var $ = __webpack_require__(747);
	__webpack_require__(757);

	//*------------------------------------------------------------------*

	var FactorTooltip = __webpack_require__(818);

	//*------------------------------------------------------------------*

	__webpack_require__(819);

	//*------------------------------------------------------------------*

	/**
	 * @param {Object} options
	 * @param {string} options.contextRoot
	 * @param {string} options.accessKey
	 * @param {string} options.experimentAccession
	 * @param {string} options.assayGroupId
	 * @param {Object} options.element
	 */
	function initTooltip(options) {

	    $(options.element).attr("title", "").tooltip({

	        hide:false,

	        show:false,

	        tooltipClass: "gxaFactorTooltip",

	        close: function() {
	            $(".gxaFactorTooltip").remove();
	        },

	        content: function (callback) {
	            $.ajax({
	                url: options.contextRoot + "/rest/assayGroup-summary",
	                data:{
	                    experimentAccession: options.experimentAccession,
	                    assayGroupId: options.assayGroupId,
	                    accessKey: options.accessKey
	                },
	                type:"GET",
	                success:function (data) {
	                    var html =
	                        ReactDOMServer.renderToString(
	                            React.createElement(
	                                FactorTooltip,
	                                {
	                                    properties: data.properties,
	                                    replicates: data.replicates
	                                }
	                            )
	                        );
	                    callback(html);
	                }
	            }).fail(function (data) {
	                    console.log("ERROR:  " + data);
	                    callback("ERROR: " + data);
	            });
	        }
	    });
	}

	//*------------------------------------------------------------------*

	exports.init = function (contextRoot, accessKey, element, experimentAccession, assayGroupId) {
	    initTooltip({contextRoot: contextRoot, accessKey: accessKey, element: element, experimentAccession: experimentAccession, assayGroupId: assayGroupId});
	};


/***/ },
/* 818 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);

	//*------------------------------------------------------------------*

	var FactorTooltip = React.createClass({
	    displayName: 'FactorTooltip',


	    propertyRow: function (property) {
	        if (!property.testValue) {
	            return null;
	        }

	        function isFactor(property) {
	            return property.contrastPropertyType === 'FACTOR';
	        }

	        var style = { 'whiteSpace': 'normal' };

	        if (isFactor(property)) {
	            style['fontWeight'] = 'bold';
	        } else {
	            style['color'] = 'gray';
	        }

	        return React.createElement(
	            'tr',
	            { key: property.propertyName },
	            React.createElement(
	                'td',
	                { style: style },
	                property.propertyName
	            ),
	            React.createElement(
	                'td',
	                { style: style },
	                property.testValue
	            )
	        );
	    },

	    render: function () {
	        return React.createElement(
	            'div',
	            null,
	            React.createElement(
	                'table',
	                null,
	                React.createElement(
	                    'thead',
	                    null,
	                    React.createElement(
	                        'tr',
	                        null,
	                        React.createElement(
	                            'th',
	                            null,
	                            'Property'
	                        ),
	                        React.createElement(
	                            'th',
	                            null,
	                            'Value (N=',
	                            this.props.replicates,
	                            ')'
	                        )
	                    )
	                ),
	                React.createElement(
	                    'tbody',
	                    null,
	                    this.props.properties.map(this.propertyRow)
	                )
	            )
	        );
	    }
	});

	//*------------------------------------------------------------------*

	module.exports = FactorTooltip;

/***/ },
/* 819 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(820);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./factor-tooltip-module.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./factor-tooltip-module.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 820 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaFactorTooltip {\n    border: solid transparent;\n    color: darkslategray;\n    padding: 2px;\n    font: 10px Verdana, Helvetica, Arial, sans-serif;\n    max-width: 500px;\n}\n\n.gxaFactorTooltip table {\n    background-color: white;\n    border: 1px solid lightgrey;\n    border-collapse: collapse;\n}\n\n.gxaFactorTooltip th {\n    border-bottom: 1px solid lightgrey;\n    background-color: floralwhite;\n}\n\n.gxaFactorTooltip td {\n    border: 1px solid lightgrey;\n    white-space: nowrap;\n}\n\n.gxaFactorTooltip td, .gxaFactorTooltip th {\n    vertical-align: middle;\n    padding: 8px;\n}\n", ""]);

	// exports


/***/ },
/* 821 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var $ = __webpack_require__(747);

	//*------------------------------------------------------------------*

	function StickyHeaderModule(table, stickyIntersect, stickyColumn, stickyHeadRow, stickyWrap, tableHeader) {
	    var $t	             = $(table),
	        $stickyIntersect = $(stickyIntersect),
	        $stickyColumn    = $(stickyColumn),
	        $stickyHeadRow   = $(stickyHeadRow),
	        $stickyWrap      = $(stickyWrap),
	        $tableHeader     = $(tableHeader);

	    var calculateAllowance = createStickyAllowanceCallback($t, $stickyHeadRow);
	    var stickyReposition = createStickyRepositionCallback($t, $stickyIntersect, $stickyColumn, $stickyHeadRow, $stickyWrap, $tableHeader, calculateAllowance);
	    var setWidthAndHeight = createSetStickyWidthHeight($t, $stickyIntersect, $stickyColumn, $stickyHeadRow, $stickyWrap);
	    var setWidthsAndReposition = createSetStickyWidthHeightAndRepositionCallback(setWidthAndHeight, stickyReposition);

	    return {
	        calculateAllowance: calculateAllowance,
	        stickyReposition: stickyReposition,
	        setWidthAndHeight: setWidthAndHeight,
	        setWidthsAndReposition: setWidthsAndReposition
	    };

	    function createSetStickyWidthHeight($t, $stickyIntersect, $stickyColumn, $stickyHeadRow, $stickyWrap) {
	        return function () {
	            $t.find("thead th").each(
	                function (i) {
	                    $stickyHeadRow.find("th").eq(i).width($(this).width());
	                }
	            ).end().find("tr").each(
	                function (i) {
	                    $stickyColumn.find("tr").eq(i).height($(this).height());
	                    $stickyIntersect.find("tr").eq(i).height($(this).height());
	                }
	            );

	            // Set width of sticky header table and intersect. WebKit does it wrong...
	            if ($.browser.webkit) {
	                $stickyHeadRow
	                    .width($stickyWrap.width())
	                    .find("table")
	                    .width($t.outerWidth());
	                $stickyIntersect.find("table").width($t.find("thead th").eq(0).outerWidth() + 1);
	                $stickyColumn.find("table").width($t.find("thead th").eq(0).outerWidth() + 1);
	            } else {
	                $stickyHeadRow
	                    .width($stickyWrap.width())
	                    .find("table")
	                    .width($t.width());
	                $stickyIntersect.find("table").width($t.find("thead th").eq(0).outerWidth());
	                $stickyColumn.find("table").width($t.find("thead th").eq(0).outerWidth());
	            }

	            // Set width of sticky table col
	            $stickyIntersect.find("tr:nth-child(2) th").each(function (i) {
	                $(this).width($t.find("tr:nth-child(2) th").eq(i).width());
	            });
	        }
	    }

	    function createStickyRepositionCallback($t, $stickyIntersect, $stickyColumn, $stickyHeadRow, $stickyWrap, $tableHeader, stickyAllowanceCallback) {
	        return function() {
	            var $w = $(window);

	            // Set position sticky col
	            $stickyHeadRow.add($stickyIntersect).add($stickyColumn).css({
	                left: $stickyWrap.offset().left,
	                top: $stickyWrap.offset().top
	            });

	            var allowance = stickyAllowanceCallback();

	            $stickyHeadRow.find("table").css({
	                left: -$stickyWrap.scrollLeft()
	            });
	            $stickyColumn.css({
	                top: $stickyWrap.offset().top - $w.scrollTop(),
	                left: $stickyWrap.offset().left
	            });

	            // 1. Position sticky header based on viewport scrollTop
	            if ($w.scrollTop() + $tableHeader.outerHeight() > $t.offset().top &&
	                $w.scrollTop() + $tableHeader.outerHeight() < $t.offset().top + $t.outerHeight() - allowance) {
	                // When top of viewport is in the table itself
	                $stickyHeadRow.add($stickyIntersect).css({
	                    visibility: "visible",
	                    top: $tableHeader.outerHeight()
	                });
	            } else if ($w.scrollTop() + $tableHeader.outerHeight() > $t.offset().top + $t.outerHeight() - allowance) {
	                $stickyHeadRow.add($stickyIntersect).css({
	                    visibility: "visible",
	                    top: $t.offset().top + $t.outerHeight() - allowance - $w.scrollTop()
	                });
	            } else {
	                // When top of viewport is above or below table
	                $stickyHeadRow.add($stickyIntersect).css({
	                    visibility: "hidden",
	                    top: $stickyWrap.offset().top - $w.scrollTop()
	                });
	            }

	            // 2. Now deal with positioning of sticky column
	            if($stickyWrap.scrollLeft() > 0) {
	                // When left of wrapping parent is out of view
	                $stickyColumn.css({
	                    visibility: "visible",
	                    "z-index": 40
	                });
	            } else {
	                $stickyColumn.css({
	                    visibility: "hidden",
	                    "z-index": -5
	                });
	            }
	        }
	    }

	    function createSetStickyWidthHeightAndRepositionCallback(setWidthAndHeightCallback, stickyRepositionCallback) {
	        return function () {
	            setWidthAndHeightCallback();
	            stickyRepositionCallback();
	        }
	    }

	    function createStickyAllowanceCallback($t, $stickyHeadRow) {
	        return function() {
	            var rowHeight = 0;
	            // Calculate allowance
	            $t.find("tbody tr:lt(1)").each(function () {
	                rowHeight += $(this).height();
	            });
	            return rowHeight + $stickyHeadRow.height();
	        }
	    }
	}

	//*------------------------------------------------------------------*

	module.exports = StickyHeaderModule;


/***/ },
/* 822 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(823);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./sticky-header-module.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./sticky-header-module.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 823 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaStickyTableWrap {\n    overflow-x: auto;\n    overflow-y: hidden;\n    position: relative;\n    width: 100%;\n}\n\n.gxaStickyTableWrap div[class^='gxaSticky'] {\n    overflow: hidden;\n}\n.gxaStickyTableWrap tfoot {\n    display: none;\n}\n.gxaStickyTableWrap div table {\n    margin: 0;\n    position: relative;\n    width: auto; /* Prevent table from stretching to full size */\n    border-collapse: collapse;\n}\n.gxaStickyTableWrap .gxaStickyTableHeader,\n.gxaStickyTableWrap .gxaStickyTableColumn,\n.gxaStickyTableWrap .gxaStickyTableIntersect {\n    visibility: hidden;\n    position: fixed;\n    z-index: 40;\n}\n.gxaStickyTableWrap .gxaStickyTableHeader {\n    z-index: 50;\n    width: 100%; /* Force stretch */\n}\n.gxaStickyTableWrap .gxaStickyTableIntersect {\n    z-index: 60;\n}\n.gxaStickyTableWrap td,\n.gxaStickyTableWrap th {\n    box-sizing: border-box;\n}\n.gxaStickyTableWrap thead th {\n    -webkit-user-select: none;\n    -moz-user-select: none;\n    -ms-user-select: none;\n}\n.gxaStickyEnabled {\n    margin: 0;\n    width: auto;\n}\n\n.wrapper-sticky {\n    z-index: 45;\n}\n\n/* To hide sticky column and intersect when screen gets too narrow */\n@media only screen and (max-width: 768px) {\n    .gxaStickyTableColumn, .gxaStickyTableIntersect {\n        display: none;\n    }\n}\n", ""]);

	// exports


/***/ },
/* 824 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(825);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./heatmap.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./heatmap.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 825 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaHeatmapMatrixTopLeftCorner {\n    position: relative;\n    display: table;\n    height: 110px;\n    width: 100%;\n    min-width: 160px;\n}\n\n.gxaTableGrid {\n    color: #404040;\n    background-color: white;\n    border: 1px solid #cdcdcd !important;\n    border-spacing: 0;\n    empty-cells: show;\n    height: 100%;\n    text-align: left;\n    width: auto;\n    border-collapse: collapse;\n}\n\n.gxaTableGrid>tbody>tr>td, .gxaTableGrid>thead>tr>td {\n    color: #3D3D3D;\n    vertical-align: middle;\n    border: 1px solid #cdcdcd !important;\n    height: 25px;\n    width: 25px;\n    white-space: nowrap;\n}\n\nth.gxaVerticalHeaderCell, .gxaHorizontalHeaderCell {\n    font-weight: normal;\n    background-color: rgb(237, 246, 246) !important;\n}\n\nth.gxaHoverableHeader:hover, th.gxaHeaderHover {\n    background-color: #deebeb !important;\n}\n\nth.gxaSelectableHeader:hover {\n    cursor: pointer;\n}\n\nth.gxaVerticalHeaderCell-selected, th.gxaVerticalHeaderCell-selected:hover, th.gxaHorizontalHeaderCell-selected, th.gxaHorizontalHeaderCell-selected:hover {\n    background-color: rgb(181, 234, 234) !important;\n    border: 1px solid #cdcdcd;\n    padding:5px;\n}\n\nth.gxaHorizontalHeaderCell {\n    border: 1px solid #cdcdcd;\n    white-space: nowrap;\n    padding:5px;\n    text-align: left !important;\n}\n\ntr.gxaProteomicsExperiment td.gxaHorizontalHeaderCell {\n    background-color: rgb(210, 233, 233) !important;\n}\n\ntr.gxaProteomicsExperiment td.gxaHorizontalHeaderCell-selected, tr.gxaProteomicsExperiment td.gxaHorizontalHeaderCell:hover {\n    background-color: rgb(200, 220, 220) !important;\n}\n\n.gxaHeatmapCell {\n    font-size: 9px;\n    background-color: white;\n    margin: 4px;\n    padding: 2px;\n    white-space: nowrap;\n    text-align: center;\n}\n\nth.gxaHeatmapTableDesignElement {\n    font-weight: normal;\n    text-align: left;\n    border: 1px solid #CDCDCD;\n}\n\n.gxaHeatmapCountAndLegend {\n    background: white;\n}\n\n.csstransforms .rotated_cell {\n    height: 130px;\n    border: 1px solid #cdcdcd;\n    vertical-align: bottom;\n    padding-bottom: 10px\n}\n\n.csstransforms .rotate_text {\n    position: relative;\n    top: 27px;\n    width: 25px;\n    padding-top: 5px;\n    white-space: nowrap;\n    -moz-transform: rotate(-90deg);\n    -moz-transform-origin: top left;\n    -ms-transform: rotate(-90deg);\n    -ms-transform-origin: top left;\n    -webkit-transform: rotate(-90deg);\n    -webkit-transform-origin: top left;\n    -o-transform: rotate(-90deg);\n    -o-transform-origin: top left;\n}\n\n.csstransforms .rotate_tick {\n    -moz-transform: rotate(-270deg);\n    -webkit-transform: rotate(-270deg);\n    -ms-transform: rotate(-270deg);\n    -o-transform: rotate(-270deg);\n}\n\n.gxaNoTextButton .ui-button-text {\n    padding: 2px;\n}", ""]);

	// exports


/***/ },
/* 826 */,
/* 827 */,
/* 828 */,
/* 829 */,
/* 830 */,
/* 831 */,
/* 832 */,
/* 833 */,
/* 834 */,
/* 835 */,
/* 836 */,
/* 837 */,
/* 838 */,
/* 839 */,
/* 840 */,
/* 841 */,
/* 842 */,
/* 843 */,
/* 844 */,
/* 845 */,
/* 846 */,
/* 847 */,
/* 848 */,
/* 849 */,
/* 850 */,
/* 851 */,
/* 852 */,
/* 853 */,
/* 854 */,
/* 855 */,
/* 856 */,
/* 857 */,
/* 858 */
/***/ function(module, exports) {

	"use strict";

	//*------------------------------------------------------------------*

	module.exports = {
	    BASELINE: { isBaseline: true, heatmapTooltip: "#heatMapTableCellInfo-baseline" },
	    PROTEOMICS_BASELINE: { isBaseline: true, isProteomics: true, heatmapTooltip: "#heatMapTableCellInfo-proteomics" },
	    DIFFERENTIAL: { isDifferential: true, heatmapTooltip: "#heatMapTableCellInfo-differential" },
	    MULTIEXPERIMENT: { isMultiExperiment: true, heatmapTooltip: "#heatMapTableCellInfo-multiexperiment" }
	};


/***/ },
/* 859 */,
/* 860 */,
/* 861 */,
/* 862 */,
/* 863 */,
/* 864 */,
/* 865 */,
/* 866 */,
/* 867 */,
/* 868 */,
/* 869 */,
/* 870 */,
/* 871 */,
/* 872 */,
/* 873 */,
/* 874 */,
/* 875 */,
/* 876 */,
/* 877 */,
/* 878 */,
/* 879 */,
/* 880 */,
/* 881 */,
/* 882 */,
/* 883 */,
/* 884 */,
/* 885 */,
/* 886 */,
/* 887 */,
/* 888 */,
/* 889 */,
/* 890 */,
/* 891 */,
/* 892 */,
/* 893 */,
/* 894 */,
/* 895 */,
/* 896 */,
/* 897 */,
/* 898 */,
/* 899 */,
/* 900 */,
/* 901 */,
/* 902 */,
/* 903 */,
/* 904 */,
/* 905 */,
/* 906 */,
/* 907 */,
/* 908 */,
/* 909 */,
/* 910 */,
/* 911 */,
/* 912 */,
/* 913 */,
/* 914 */,
/* 915 */,
/* 916 */,
/* 917 */,
/* 918 */,
/* 919 */,
/* 920 */,
/* 921 */,
/* 922 */,
/* 923 */,
/* 924 */,
/* 925 */,
/* 926 */,
/* 927 */,
/* 928 */,
/* 929 */,
/* 930 */,
/* 931 */,
/* 932 */,
/* 933 */,
/* 934 */,
/* 935 */,
/* 936 */,
/* 937 */,
/* 938 */,
/* 939 */,
/* 940 */,
/* 941 */,
/* 942 */,
/* 943 */,
/* 944 */,
/* 945 */,
/* 946 */,
/* 947 */,
/* 948 */,
/* 949 */,
/* 950 */,
/* 951 */,
/* 952 */,
/* 953 */,
/* 954 */,
/* 955 */,
/* 956 */,
/* 957 */,
/* 958 */,
/* 959 */,
/* 960 */,
/* 961 */,
/* 962 */,
/* 963 */,
/* 964 */,
/* 965 */,
/* 966 */,
/* 967 */,
/* 968 */,
/* 969 */,
/* 970 */,
/* 971 */,
/* 972 */,
/* 973 */,
/* 974 */,
/* 975 */,
/* 976 */,
/* 977 */,
/* 978 */,
/* 979 */,
/* 980 */,
/* 981 */,
/* 982 */,
/* 983 */,
/* 984 */,
/* 985 */,
/* 986 */,
/* 987 */,
/* 988 */,
/* 989 */,
/* 990 */,
/* 991 */,
/* 992 */,
/* 993 */,
/* 994 */,
/* 995 */,
/* 996 */,
/* 997 */,
/* 998 */,
/* 999 */,
/* 1000 */,
/* 1001 */,
/* 1002 */,
/* 1003 */,
/* 1004 */,
/* 1005 */,
/* 1006 */,
/* 1007 */,
/* 1008 */,
/* 1009 */,
/* 1010 */,
/* 1011 */,
/* 1012 */,
/* 1013 */,
/* 1014 */,
/* 1015 */,
/* 1016 */,
/* 1017 */,
/* 1018 */,
/* 1019 */,
/* 1020 */,
/* 1021 */,
/* 1022 */,
/* 1023 */,
/* 1024 */,
/* 1025 */,
/* 1026 */,
/* 1027 */,
/* 1028 */,
/* 1029 */,
/* 1030 */,
/* 1031 */,
/* 1032 */,
/* 1033 */,
/* 1034 */,
/* 1035 */,
/* 1036 */,
/* 1037 */,
/* 1038 */,
/* 1039 */,
/* 1040 */,
/* 1041 */,
/* 1042 */,
/* 1043 */,
/* 1044 */,
/* 1045 */,
/* 1046 */,
/* 1047 */,
/* 1048 */,
/* 1049 */,
/* 1050 */,
/* 1051 */,
/* 1052 */,
/* 1053 */,
/* 1054 */,
/* 1055 */,
/* 1056 */,
/* 1057 */,
/* 1058 */,
/* 1059 */,
/* 1060 */,
/* 1061 */,
/* 1062 */,
/* 1063 */,
/* 1064 */,
/* 1065 */,
/* 1066 */,
/* 1067 */,
/* 1068 */,
/* 1069 */,
/* 1070 */,
/* 1071 */,
/* 1072 */,
/* 1073 */,
/* 1074 */,
/* 1075 */,
/* 1076 */,
/* 1077 */,
/* 1078 */,
/* 1079 */,
/* 1080 */,
/* 1081 */,
/* 1082 */,
/* 1083 */,
/* 1084 */,
/* 1085 */,
/* 1086 */,
/* 1087 */,
/* 1088 */,
/* 1089 */,
/* 1090 */,
/* 1091 */,
/* 1092 */,
/* 1093 */,
/* 1094 */,
/* 1095 */,
/* 1096 */,
/* 1097 */,
/* 1098 */,
/* 1099 */,
/* 1100 */,
/* 1101 */,
/* 1102 */,
/* 1103 */,
/* 1104 */,
/* 1105 */,
/* 1106 */,
/* 1107 */,
/* 1108 */,
/* 1109 */,
/* 1110 */,
/* 1111 */,
/* 1112 */,
/* 1113 */,
/* 1114 */,
/* 1115 */,
/* 1116 */,
/* 1117 */,
/* 1118 */,
/* 1119 */,
/* 1120 */,
/* 1121 */,
/* 1122 */,
/* 1123 */,
/* 1124 */,
/* 1125 */,
/* 1126 */,
/* 1127 */,
/* 1128 */,
/* 1129 */,
/* 1130 */,
/* 1131 */,
/* 1132 */,
/* 1133 */,
/* 1134 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);

	var $ = __webpack_require__(747);
	__webpack_require__(749);

	var EventEmitter = __webpack_require__(160);

	//*------------------------------------------------------------------*

	var Anatomogram = __webpack_require__(754);
	var Heatmap = __webpack_require__(759);
	var EnsemblLauncher = __webpack_require__(1135);

	var ExperimentTypes = __webpack_require__(858);

	//*------------------------------------------------------------------*

	__webpack_require__(1139);

	//*------------------------------------------------------------------*

	var InternalHeatmapAnatomogramContainer = React.createClass({
	    displayName: 'InternalHeatmapAnatomogramContainer',

	    propTypes: {
	        anatomogram: React.PropTypes.object,
	        columnHeaders: React.PropTypes.oneOfType([React.PropTypes.arrayOf(React.PropTypes.shape({
	            assayGroupId: React.PropTypes.string.isRequired,
	            factorValue: React.PropTypes.string.isRequired,
	            factorValueOntologyTermId: React.PropTypes.string
	        })), React.PropTypes.arrayOf(React.PropTypes.shape({
	            id: React.PropTypes.string.isRequired,
	            referenceAssayGroup: React.PropTypes.shape({
	                id: React.PropTypes.string.isRequired,
	                assayAccessions: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	                replicates: React.PropTypes.number.isRequired
	            }).isRequired,
	            testAssayGroup: React.PropTypes.shape({
	                id: React.PropTypes.string.isRequired,
	                assayAccessions: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	                replicates: React.PropTypes.number.isRequired
	            }).isRequired,
	            displayName: React.PropTypes.string.isRequired
	        }))]).isRequired,
	        nonExpressedColumnHeaders: React.PropTypes.arrayOf(React.PropTypes.string).isRequired,
	        multipleColumnHeaders: React.PropTypes.object,
	        profiles: React.PropTypes.object.isRequired,
	        geneSetProfiles: React.PropTypes.object,
	        heatmapConfig: React.PropTypes.object.isRequired,
	        type: React.PropTypes.oneOf([ExperimentTypes.BASELINE, ExperimentTypes.MULTIEXPERIMENT, ExperimentTypes.DIFFERENTIAL, ExperimentTypes.PROTEOMICS_BASELINE]).isRequired,
	        isWidget: React.PropTypes.bool.isRequired,
	        atlasBaseURL: React.PropTypes.string.isRequired,
	        linksAtlasBaseURL: React.PropTypes.string.isRequired
	    },

	    render: function () {
	        var ensemblEventEmitter = new EventEmitter();
	        var anatomogramEventEmitter = new EventEmitter();

	        var anatomogramExpressedTissueColour = this.props.type.isMultiExperiment ? "red" : "gray";
	        var anatomogramHoveredTissueColour = this.props.type.isMultiExperiment ? "indigo" : "red";

	        var prefFormDisplayLevels = $('#displayLevels');

	        return React.createElement(
	            'div',
	            { id: 'heatmap-anatomogram', className: 'gxaHeatmapAnatomogramRow' },
	            React.createElement(
	                'div',
	                { ref: 'anatomogramEnsembl', className: 'gxaAside' },
	                this.props.anatomogram ? React.createElement(Anatomogram, { anatomogramData: this.props.anatomogram,
	                    expressedTissueColour: anatomogramExpressedTissueColour, hoveredTissueColour: anatomogramHoveredTissueColour,
	                    profileRows: this.props.profiles.rows, eventEmitter: anatomogramEventEmitter, atlasBaseURL: this.props.atlasBaseURL }) : null,
	                this.props.heatmapConfig.enableEnsemblLauncher ? React.createElement(EnsemblLauncher, { isBaseline: this.props.type === ExperimentTypes.BASELINE || this.props.type === ExperimentTypes.PROTEOMICS_BASELINE,
	                    experimentAccession: this.props.heatmapConfig.experimentAccession,
	                    species: this.props.heatmapConfig.species,
	                    ensemblDB: this.props.heatmapConfig.ensemblDB,
	                    columnType: this.props.heatmapConfig.columnType,
	                    eventEmitter: ensemblEventEmitter,
	                    atlasBaseURL: this.props.atlasBaseURL }) : null
	            ),
	            React.createElement(
	                'div',
	                { id: 'heatmap-react', className: 'gxaHeatmapPosition' },
	                React.createElement(Heatmap, { type: this.props.type,
	                    heatmapConfig: this.props.heatmapConfig,
	                    columnHeaders: this.props.columnHeaders,
	                    nonExpressedColumnHeaders: this.props.nonExpressedColumnHeaders,
	                    multipleColumnHeaders: this.props.multipleColumnHeaders,
	                    profiles: this.props.profiles,
	                    geneSetProfiles: this.props.geneSetProfiles,
	                    isWidget: false,
	                    prefFormDisplayLevels: prefFormDisplayLevels,
	                    ensemblEventEmitter: ensemblEventEmitter,
	                    anatomogramEventEmitter: anatomogramEventEmitter,
	                    atlasBaseURL: this.props.atlasBaseURL,
	                    linksAtlasBaseURL: this.props.linksAtlasBaseURL })
	            )
	        );
	    },

	    componentDidMount: function () {
	        var $anatomogramEnsemblAside = $(ReactDOM.findDOMNode(this.refs.anatomogramEnsembl));
	        $anatomogramEnsemblAside.hcSticky({ responsive: true });
	    }
	});

	//*------------------------------------------------------------------*

	module.exports = InternalHeatmapAnatomogramContainer;

/***/ },
/* 1135 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	//*------------------------------------------------------------------*

	var React = __webpack_require__(589);
	var ReactDOM = __webpack_require__(745);

	var $ = __webpack_require__(747);
	__webpack_require__(757);

	//*------------------------------------------------------------------*

	var ensemblUtils = __webpack_require__(1136);

	//*------------------------------------------------------------------*

	__webpack_require__(1137);

	//*------------------------------------------------------------------*

	var EnsemblLauncher = React.createClass({
	    displayName: 'EnsemblLauncher',

	    propTypes: {
	        isBaseline: React.PropTypes.bool.isRequired,
	        experimentAccession: React.PropTypes.string.isRequired,
	        species: React.PropTypes.string.isRequired,
	        ensemblDB: React.PropTypes.string.isRequired,
	        columnType: React.PropTypes.string.isRequired,
	        eventEmitter: React.PropTypes.object.isRequired,
	        atlasBaseURL: React.PropTypes.string.isRequired
	    },

	    _noSelectedColumnMessageArticle: function () {
	        var isVowel = function () {
	            var re = /^[aeiou]$/i;
	            return function (char) {
	                return re.test(char);
	            };
	        }();

	        var beginsWithVowel = function (string) {
	            return isVowel(string.charAt(0));
	        };

	        return beginsWithVowel(this.props.columnType) ? "an " : "a ";
	    },

	    _ensemblTrackURL: function (baseURL, selectedColumnId, selectedGeneId) {
	        var ensemblSpecies = ensemblUtils.toEnsemblSpecies(this.props.species);
	        var atlasTrackBaseURLWithTrackFileHeader = window.location.protocol + "//" + window.location.host + this.props.atlasBaseURL + "/experiments/" + this.props.experimentAccession + "/tracks/" + this.props.experimentAccession + "." + selectedColumnId;
	        var contigViewBottom = "contigviewbottom=url:" + atlasTrackBaseURLWithTrackFileHeader + (this.props.isBaseline ? ".genes.expressions.bedGraph" : ".genes.log2foldchange.bedGraph");
	        var tiling = this.props.isBaseline ? "" : "=tiling,url:" + atlasTrackBaseURLWithTrackFileHeader + ".genes.pval.bedGraph=pvalue;";
	        return baseURL + ensemblSpecies + "/Location/View?g=" + selectedGeneId + ";db=core;" + contigViewBottom + tiling + ";format=BEDGRAPH";
	    },

	    _openEnsemblWindow: function (baseURL) {
	        if (!this.state.selectedColumnId || !this.state.selectedGeneId) {
	            return;
	        }
	        window.open(this._ensemblTrackURL(baseURL, this.state.selectedColumnId, this.state.selectedGeneId), '_blank');
	    },

	    _onColumnSelectionChange: function (selectedColumnId) {
	        this.setState({ selectedColumnId: selectedColumnId });
	    },

	    _onGeneSelectionChange: function (selectedGeneId) {
	        this.setState({ selectedGeneId: selectedGeneId });
	    },

	    _updateButton: function () {
	        var buttonEnabled = this.state.selectedColumnId && this.state.selectedGeneId ? true : false;
	        $(ReactDOM.findDOMNode(this.refs.ensemblButton)).button("option", "disabled", !buttonEnabled);
	        if (this.props.ensemblDB == "plants") {
	            $(ReactDOM.findDOMNode(this.refs.grameneButton)).button("option", "disabled", !buttonEnabled);
	        }
	    },

	    componentDidUpdate: function () {
	        this._updateButton();
	    },

	    componentDidMount: function () {
	        $(ReactDOM.findDOMNode(this.refs.ensemblButton)).button({ icons: { primary: "ui-icon-newwin" } });
	        if (this.props.ensemblDB == "plants") {
	            $(ReactDOM.findDOMNode(this.refs.grameneButton)).button({ icons: { primary: "ui-icon-newwin" } });
	        }
	        this._updateButton();
	        this.props.eventEmitter.addListener('onColumnSelectionChange', this._onColumnSelectionChange);
	        this.props.eventEmitter.addListener('onGeneSelectionChange', this._onGeneSelectionChange);
	    },

	    getInitialState: function () {
	        return { selectedColumnId: null, selectedGeneId: null, buttonText: "" };
	    },

	    render: function () {
	        var ensemblHost = ensemblUtils.getEnsemblHost(this.props.ensemblDB);
	        var grameneHost = ensemblUtils.getGrameneHost();

	        return React.createElement(
	            'div',
	            { id: 'ensembl-launcher-box', style: { width: "245px" } },
	            React.createElement(
	                'div',
	                { id: 'ensembl-launcher-box-ensembl' },
	                React.createElement(
	                    'div',
	                    { className: 'gxaEnsemblGrameneLauncherHeader' },
	                    React.createElement(
	                        'label',
	                        null,
	                        'Ensembl Genome Browser'
	                    ),
	                    React.createElement('img', { src: this.props.atlasBaseURL + "/resources/images/ensembl.png", style: { padding: "0px 5px" } })
	                ),
	                React.createElement(
	                    'button',
	                    { ref: 'ensemblButton', onClick: this._openEnsemblWindow.bind(this, ensemblHost) },
	                    'Open'
	                )
	            ),
	            this.props.ensemblDB == "plants" ? React.createElement(
	                'div',
	                { id: 'ensembl-launcher-box-gramene' },
	                React.createElement(
	                    'div',
	                    { className: 'gxaEnsemblGrameneLauncherHeader' },
	                    React.createElement(
	                        'label',
	                        null,
	                        'Gramene Genome Browser'
	                    ),
	                    React.createElement('img', { src: this.props.atlasBaseURL + "/resources/images/gramene.png", style: { padding: "0px 5px" } })
	                ),
	                React.createElement(
	                    'button',
	                    { ref: 'grameneButton', onClick: this._openEnsemblWindow.bind(this, grameneHost) },
	                    'Open'
	                )
	            ) : null,
	            React.createElement(
	                'div',
	                { style: { "fontSize": "x-small", height: "30px", padding: "9px 9px" } },
	                this._helpMessage(this.state.selectedColumnId, this.state.selectedGeneId)
	            )
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

/***/ },
/* 1136 */
/***/ function(module, exports) {

	"use strict";

	//*------------------------------------------------------------------*

	// ensemblSpecies is the first two words only, with underscores instead of spaces, and all lower case except for the first character
	// used to launch the ensembl genome browser for tracks
	/**
	 * @param {string} species
	 */
	function toEnsemblSpecies(species) {
	    /**
	     * @param {string} str
	     */
	    function capitaliseFirstLetter(str)
	    {
	        return str.charAt(0).toUpperCase() + str.slice(1);
	    }

	    /**
	     * @param {string} str
	     */
	    function firstTwoWords(str) {
	        var words = str.split(" ");
	        return (words.length <= 2) ? str : words[0] + " " + words[1];
	    }

	    return capitaliseFirstLetter(firstTwoWords(species).replace(" ", "_").toLowerCase());
	}

	function getEnsemblHost(ensemblDB) {
	    var ensemblHost = "";

	    if (ensemblDB === "plants") {
	        ensemblHost = "http://plants.ensembl.org/";
	    } else if (ensemblDB === "fungi") {
	        ensemblHost = "http://fungi.ensembl.org/";
	    } else if (ensemblDB === "metazoa") {
	        ensemblHost = "http://metazoa.ensembl.org/";
	    } else if (ensemblDB === "ensembl") {
	        ensemblHost = "http://www.ensembl.org/";
	    }

	    return ensemblHost;
	}

	function getGrameneHost() {
	    return "http://ensembl.gramene.org/";
	}

	//*------------------------------------------------------------------*

	exports.toEnsemblSpecies = toEnsemblSpecies;
	exports.getEnsemblHost = getEnsemblHost;
	exports.getGrameneHost = getGrameneHost;

/***/ },
/* 1137 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(1138);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./ensembl-launcher.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./ensembl-launcher.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 1138 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, "#ensembl-launcher-box {\n    border: 1px solid #cdcdcd;\n    text-align: center;\n}\n\n#ensembl-launcher-box-ensembl, #ensembl-launcher-box-gramene {\n    padding: 4px 9px;\n}\n\n#ensembl-launcher-box-ensembl label, #ensembl-launcher-box-gramene label {\n    font-weight: bold;\n    font-family: Helvetica, sans-serif;\n    font-size: 14px;\n}\n\n#ensembl-launcher-box-ensembl button, #ensembl-launcher-box-gramene button {\n    display: table;\n    margin: 0 auto;\n    font-size: 14px;\n}\n\n.gxaEnsemblGrameneLauncherHeader {\n\n    padding-bottom: 6px;\n}\n", ""]);

	// exports


/***/ },
/* 1139 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(1140);
	if(typeof content === 'string') content = [[module.id, content, '']];
	// add the styles to the DOM
	var update = __webpack_require__(774)(content, {});
	if(content.locals) module.exports = content.locals;
	// Hot Module Replacement
	if(false) {
		// When the styles change, update the <style> tags
		if(!content.locals) {
			module.hot.accept("!!./../node_modules/css-loader/index.js!./internal-heatmap-anatomogram-container.css", function() {
				var newContent = require("!!./../node_modules/css-loader/index.js!./internal-heatmap-anatomogram-container.css");
				if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
				update(newContent);
			});
		}
		// When the module is disposed, remove the <style> tags
		module.hot.dispose(function() { update(); });
	}

/***/ },
/* 1140 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(767)();
	// imports


	// module
	exports.push([module.id, ".gxaHeatmapPosition {\n    position: relative;\n    margin-left: 270px;\n    overflow: hidden;\n}\n\n.gxaAside {\n    float: left;\n}", ""]);

	// exports


/***/ }
]);
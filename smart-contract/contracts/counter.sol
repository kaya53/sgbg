// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.5.0;

contract Counter {
    int256 public value;

    function add() public {
        value = value + 1;
    }

    function sub() public {
        value = value - 1;
    }
}
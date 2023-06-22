// https://eips.ethereum.org/EIPS/eip-20
// SPDX-License-Identifier: MIT
pragma solidity >=0.7.0;

import "./IERC20.sol";

contract Cash is IERC20 {
    uint256 constant private MAX_UINT256 = 2**256 - 1;

    mapping (address => uint256) public balances;
    mapping (address => mapping (address => uint256)) public allowed;

    address private creator; // owner
    uint256 private totalSupply; // total token supply

    string public name = "SgbgToken"; // Name for display purposes
    string public symbol = "SBTKN"; // Symbol for display purposes
    uint8 public decimals = 18; // Amount of decimals for display purposes

    constructor(uint256 _initialAmount) {
        creator = msg.sender; // Admin of SGBG
        balances[creator] = _initialAmount; // Give the creator all initial tokens
        totalSupply = _initialAmount; // Update total supply
    }

    /**
     * @notice mint: create `_amount` of token and alloc to `_receiver`
     * @param _receiver the receiver's address
     * @param _amount the amount of tokens
     */
    function mint(address _receiver, uint256 _amount) external {
        require(_receiver != address(0), "mint to zero address");
        totalSupply += _amount;
        balances[msg.sender] += _amount;
        emit Transfer(address(0), msg.sender, _amount);
    }

    /**
     * @notice send `_value` token to `_to` from `msg.sender`
     * @param _to The address of the recipient
     * @param _value The amount of token to be transferred
     * @return success Whether the transfer was successful or not
     */
    function transfer(address _to, uint256 _value) public override returns (bool success) {
        require(balances[msg.sender] >= _value, "token balance is lower than the value requested");
        balances[msg.sender] -= _value;
        balances[_to] += _value;
        emit Transfer(msg.sender, _to, _value);
        return true;
    }

    /**
     * @notice send `_value` token to `_to` from `_from` on the condition it is approved by `_from`
     * @param _from The address of the sender
     * @param _to The address of the recipient
     * @param _value The amount of token to be transferred
     * @return success Whether the transfer was successful or not
     */
    function transferFrom(address _from, address _to, uint256 _value) public override returns (bool success) {
        uint256 allowance = allowed[_from][msg.sender];
        require(balances[_from] >= _value && allowance >= _value, "token balance or allowance is lower than amount requested");
        balances[_to] += _value;
        balances[_from] -= _value;
        if (allowance < MAX_UINT256) {
            allowed[_from][msg.sender] -= _value;
        }
        emit Transfer(_from, _to, _value);
        return true;
    }

    /**
     * @notice retrieves the balance that the account has
     * @param _owner The address from which the balance will be retrieved
     * @return balance the balance
     */
    function balanceOf(address _owner) public override view returns (uint256 balance) {
        return balances[_owner];
    }

    /**
     * @notice `msg.sender` approves `_addr` to spend `_value` tokens
     * @param _spender The address of the account able to transfer the tokens
     * @param _value The amount of wei to be approved for transfer
     * @return success Whether the approval was successful or not
     */
    function approve(address _spender, uint256 _value) public override returns (bool success) {
        allowed[msg.sender][_spender] = _value;
        emit Approval(msg.sender, _spender, _value);
        return true;
    }

    /**
     * @param _owner The address of the account owning tokens
     * @param _spender The address of the account able to transfer the tokens
     * @return remaining Amount of remaining tokens allowed to spent
     */
    function allowance(address _owner, address _spender) public override view returns (uint256 remaining) {
        return allowed[_owner][_spender];
    }

    // /**
    //  * @notice buy tokens
    //  * msg.value should be greater than or equal to 0.1 ether
    //  * 1 eth = 100,000 cash
    //  * @return success or failure
    //  */
    // function buy() public payable returns(bool) {
    //     return false;
    // }
}
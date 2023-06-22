package com.sgbg.blockchain.wrapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Contracts_SingleBungle_sol_SingleBungle extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620012bc380380620012bc83398181016040528101906200003791906200016e565b836000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508060038190555050505050620001e0565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000620000fb82620000ce565b9050919050565b6200010d81620000ee565b81146200011957600080fd5b50565b6000815190506200012d8162000102565b92915050565b6000819050919050565b620001488162000133565b81146200015457600080fd5b50565b60008151905062000168816200013d565b92915050565b600080600080608085870312156200018b576200018a620000c9565b5b60006200019b878288016200011c565b9450506020620001ae878288016200011c565b9350506040620001c18782880162000157565b9250506060620001d48782880162000157565b91505092959194509250565b6110cc80620001f06000396000f3fe6080604052600436106100705760003560e01c8063698a14fe1161004e578063698a14fe146100f957806392cfe9dc14610122578063b0f707dc1461014d578063c39e76cc1461016957610070565b806311e4cf7d1461007557806312065fe0146100b257806351cff8d9146100dd575b600080fd5b34801561008157600080fd5b5061009c600480360381019061009791906109dd565b610185565b6040516100a99190610a38565b60405180910390f35b3480156100be57600080fd5b506100c761022e565b6040516100d49190610a62565b60405180910390f35b6100f760048036038101906100f29190610a7d565b610273565b005b34801561010557600080fd5b50610120600480360381019061011b9190610ad6565b6103a9565b005b34801561012e57600080fd5b50610137610411565b6040516101449190610b75565b60405180910390f35b610167600480360381019061016291906109dd565b610435565b005b610183600480360381019061017e91906109dd565b610621565b005b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663095ea7b384846040518363ffffffff1660e01b81526004016101e3929190610b9f565b6020604051808303816000875af1158015610202573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906102269190610bdd565b905092915050565b60008073ffffffffffffffffffffffffffffffffffffffff163073ffffffffffffffffffffffffffffffffffffffff160361026c5760009050610270565b4790505b90565b80600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614610304576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102fb90610c67565b60405180910390fd5b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3084476040518463ffffffff1660e01b815260040161036193929190610c87565b6020604051808303816000875af1158015610380573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103a49190610bdd565b505050565b600015158115150361040d5780600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055505b5050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b806003548110156104476003546107e4565b6040516020016104579190610d55565b6040516020818303038152906040526040516020016104769190610da1565b604051602081830303815290604052906104c6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104bd9190610e11565b60405180910390fd5b50600260008154809291906104da90610e62565b919050555081600460008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506001600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd8430856040518463ffffffff1660e01b81526004016105d893929190610c87565b6020604051808303816000875af11580156105f7573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061061b9190610bdd565b50505050565b816000600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054116106a4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161069b90610ef6565b60405180910390fd5b600260008154809291906106b790610f16565b919050555060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3085600460008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546040518463ffffffff1660e01b815260040161075893929190610c87565b6020604051808303816000875af1158015610777573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061079b9190610bdd565b50600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009055505050565b60606000820361082b576040518060400160405280600181526020017f3000000000000000000000000000000000000000000000000000000000000000815250905061093f565b600082905060005b6000821461085d57808061084690610e62565b915050600a826108569190610f6e565b9150610833565b60008167ffffffffffffffff81111561087957610878610f9f565b5b6040519080825280601f01601f1916602001820160405280156108ab5781602001600182028036833780820191505090505b5090505b60008514610938576001826108c49190610fce565b9150600a856108d39190611002565b60306108df9190611033565b60f81b8183815181106108f5576108f4611067565b5b60200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350600a856109319190610f6e565b94506108af565b8093505050505b919050565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061097482610949565b9050919050565b61098481610969565b811461098f57600080fd5b50565b6000813590506109a18161097b565b92915050565b6000819050919050565b6109ba816109a7565b81146109c557600080fd5b50565b6000813590506109d7816109b1565b92915050565b600080604083850312156109f4576109f3610944565b5b6000610a0285828601610992565b9250506020610a13858286016109c8565b9150509250929050565b60008115159050919050565b610a3281610a1d565b82525050565b6000602082019050610a4d6000830184610a29565b92915050565b610a5c816109a7565b82525050565b6000602082019050610a776000830184610a53565b92915050565b600060208284031215610a9357610a92610944565b5b6000610aa184828501610992565b91505092915050565b610ab381610a1d565b8114610abe57600080fd5b50565b600081359050610ad081610aaa565b92915050565b60008060408385031215610aed57610aec610944565b5b6000610afb85828601610992565b9250506020610b0c85828601610ac1565b9150509250929050565b6000819050919050565b6000610b3b610b36610b3184610949565b610b16565b610949565b9050919050565b6000610b4d82610b20565b9050919050565b6000610b5f82610b42565b9050919050565b610b6f81610b54565b82525050565b6000602082019050610b8a6000830184610b66565b92915050565b610b9981610969565b82525050565b6000604082019050610bb46000830185610b90565b610bc16020830184610a53565b9392505050565b600081519050610bd781610aaa565b92915050565b600060208284031215610bf357610bf2610944565b5b6000610c0184828501610bc8565b91505092915050565b600082825260208201905092915050565b7f4e4f5420484f5354204144445245535300000000000000000000000000000000600082015250565b6000610c51601083610c0a565b9150610c5c82610c1b565b602082019050919050565b60006020820190508181036000830152610c8081610c44565b9050919050565b6000606082019050610c9c6000830186610b90565b610ca96020830185610b90565b610cb66040830184610a53565b949350505050565b7f4d494e494d554d20414d4f554e543a2000000000000000000000000000000000815250565b600081519050919050565b600081905092915050565b60005b83811015610d18578082015181840152602081019050610cfd565b60008484015250505050565b6000610d2f82610ce4565b610d398185610cef565b9350610d49818560208601610cfa565b80840191505092915050565b6000610d6082610cbe565b601082019150610d708284610d24565b915081905092915050565b7f205342544b4e0000000000000000000000000000000000000000000000000000815250565b6000610dad8284610d24565b9150610db882610d7b565b60068201915081905092915050565b6000601f19601f8301169050919050565b6000610de382610ce4565b610ded8185610c0a565b9350610dfd818560208601610cfa565b610e0681610dc7565b840191505092915050565b60006020820190508181036000830152610e2b8184610dd8565b905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610e6d826109a7565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203610e9f57610e9e610e33565b5b600182019050919050565b7f4e4f54204120524f4f4d204d454d424552000000000000000000000000000000600082015250565b6000610ee0601183610c0a565b9150610eeb82610eaa565b602082019050919050565b60006020820190508181036000830152610f0f81610ed3565b9050919050565b6000610f21826109a7565b915060008203610f3457610f33610e33565b5b600182039050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b6000610f79826109a7565b9150610f84836109a7565b925082610f9457610f93610f3f565b5b828204905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000610fd9826109a7565b9150610fe4836109a7565b9250828203905081811115610ffc57610ffb610e33565b5b92915050565b600061100d826109a7565b9150611018836109a7565b92508261102857611027610f3f565b5b828206905092915050565b600061103e826109a7565b9150611049836109a7565b925082820190508082111561106157611060610e33565b5b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fdfea264697066735822122014821f4e634e021736b6ae1baf066d95316b5a339082a51b8b73fb4b558b7e1d64736f6c63430008110033";

    public static final String FUNC_ENTERROOM = "enterRoom";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_ISSUCCESS = "isSuccess";

    public static final String FUNC_LEAVEROOM = "leaveRoom";

    public static final String FUNC_SGBGAPPROVE = "sgbgApprove";

    public static final String FUNC_SGBGTOKEN = "sgbgToken";

    public static final String FUNC_WITHDRAW = "withdraw";

    @Deprecated
    protected Contracts_SingleBungle_sol_SingleBungle(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Contracts_SingleBungle_sol_SingleBungle(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Contracts_SingleBungle_sol_SingleBungle(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Contracts_SingleBungle_sol_SingleBungle(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> enterRoom(String _member, BigInteger _value) {
        final Function function = new Function(
                FUNC_ENTERROOM, 
                Arrays.<Type>asList(new Address(160, _member),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getBalance() {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> isSuccess(String _member, Boolean _flag) {
        final Function function = new Function(
                FUNC_ISSUCCESS, 
                Arrays.<Type>asList(new Address(160, _member),
                new org.web3j.abi.datatypes.Bool(_flag)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> leaveRoom(String _member, BigInteger _value) {
        final Function function = new Function(
                FUNC_LEAVEROOM, 
                Arrays.<Type>asList(new Address(160, _member),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sgbgApprove(String _spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_SGBGAPPROVE, 
                Arrays.<Type>asList(new Address(160, _spender),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> sgbgToken() {
        final Function function = new Function(FUNC_SGBGTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String _sender) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new Address(160, _sender)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Contracts_SingleBungle_sol_SingleBungle load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_SingleBungle_sol_SingleBungle(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Contracts_SingleBungle_sol_SingleBungle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts_SingleBungle_sol_SingleBungle(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Contracts_SingleBungle_sol_SingleBungle load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Contracts_SingleBungle_sol_SingleBungle(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Contracts_SingleBungle_sol_SingleBungle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Contracts_SingleBungle_sol_SingleBungle(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Contracts_SingleBungle_sol_SingleBungle> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _token, String _host, BigInteger _duration, BigInteger _minimumAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _token),
                new Address(160, _host),
                new Uint256(_duration),
                new Uint256(_minimumAmount)));
        return deployRemoteCall(Contracts_SingleBungle_sol_SingleBungle.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Contracts_SingleBungle_sol_SingleBungle> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _token, String _host, BigInteger _duration, BigInteger _minimumAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _token),
                new Address(160, _host),
                new Uint256(_duration),
                new Uint256(_minimumAmount)));
        return deployRemoteCall(Contracts_SingleBungle_sol_SingleBungle.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Contracts_SingleBungle_sol_SingleBungle> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _token, String _host, BigInteger _duration, BigInteger _minimumAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _token),
                new Address(160, _host),
                new Uint256(_duration),
                new Uint256(_minimumAmount)));
        return deployRemoteCall(Contracts_SingleBungle_sol_SingleBungle.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Contracts_SingleBungle_sol_SingleBungle> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _token, String _host, BigInteger _duration, BigInteger _minimumAmount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _token),
                new Address(160, _host),
                new Uint256(_duration),
                new Uint256(_minimumAmount)));
        return deployRemoteCall(Contracts_SingleBungle_sol_SingleBungle.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}

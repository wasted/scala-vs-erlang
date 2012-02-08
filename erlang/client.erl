-module(client).

-export([runTest/1,runTest2/1,runTest25/1,runTest3/1]).

runTest(Size) -> 
	server:start_link(),
	Start=now(),
	Count=test(Size),
	Finish=now(),
	server:stop(),
	print_results(Count,Size,Start,Finish).
	
runTest2(Size) ->
	server2:start_link(),
	Start=now(),
	Count=test2(Size),
	Finish=now(),
	server2:stop(),
	print_results(Count,Size,Start,Finish).

runTest25(Size) ->
	P=server2:start_link(),
	Start=now(),
	Count=test2(P,Size),
	Finish=now(),
	server2:stop(),
	print_results(Count,Size,Start,Finish).

runTest3(Size) ->
	P = server2:start_link(),
	Start=now(),
	Count=test3(P,Size),
	Finish=now(),
	server2:stop(),
	print_results(Count,Size,Start,Finish).

test(Size) ->
	plists:foreach(fun (_X)-> server:bytes(100) end,lists:seq(1,Size)),
	server:get_count().

test2(PID,Size) ->
	plists:foreach(fun (_X) -> server2:bytes(PID,100) end,lists:seq(1,Size)),
	server2:get_count(PID).

test2(Size) ->
	plists:foreach(fun (_X)-> server2:bytes(100) end,lists:seq(1,Size)),
	server2:get_count().	

test3(Pid,Size) ->
	NProcs = erlang:system_info(logical_processors),
	SMsgs = round(Size/NProcs),
	Pids = test3_launch(NProcs,SMsgs,Pid),
	lists:foreach(fun(CPid) -> receive {CPid,done} -> ok end end, Pids),
	server2:get_count(Pid).

test3_launch(0,_,_) -> [];
test3_launch(N,SMsgs,Pid) ->
	Self = self(),
	[spawn(fun() -> test3_broadcast(SMsgs,Pid,Self) end) | 
		test3_launch(N-1,SMsgs,Pid)].

test3_broadcast(0,_,ParentPid) -> 
	ParentPid ! {self(),done};
test3_broadcast(SMsgs,Pid,ParentPid) -> 
	server2:bytes(Pid,100),
	test3_broadcast(SMsgs-1,Pid,ParentPid).

print_results(Count,Size,Start,Finish) ->
	io:format("Count is ~p~n",[Count]),
	io:format("Test took ~p seconds~n",[elapsedTime(Start,Finish)]),
	io:format("Throughput=~p per sec~n",[throughput(Size,Start,Finish)]).

elapsedTime(Start,Finish) -> 
    timer:now_diff(Finish, Start) / 1000000.

throughput(Size,Start,Finish) -> Size / elapsedTime(Start,Finish).

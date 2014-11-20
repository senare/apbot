package ao.apbot.codec;

public class Safepay {
	public static Safepay createResponse(String id, String location) {
		return new Response(id, location);
	}

	public static Safepay createRequest(String info, String data) {
		return new Request(info, data);
	}

	static Safepay createRequest() {
		return new Request("", "");
	}
}

class Request extends Safepay {

	String status = "55";
	String info;
	String data;

	protected Request(String info, String data) {
		this.data = data;
		this.info = info;
	}

	@Override
	public String toString() {
		return String.format("Safepay %s %s", info.equals("1") ? "ON" : "OFF", data);
	}
}

class Response extends Safepay {

	String id;
	String location;

	protected Response(String id, String location) {
		this.id = id;
		this.location = location;
	}

	@Override
	public String toString() {
		return String.format("Safepay id:%s location:%s", id, location);
	}
}
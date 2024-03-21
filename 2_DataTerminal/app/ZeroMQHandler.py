import zmq
import time


class ZeroMQPublisher:
    def __init__(self, ip, port):
        self.ip = ip
        self.port = port
        self.zmq_context = zmq.Context()
        self.zmq_socket = self.zmq_context.socket(zmq.PUB)
        self.zmq_socket.bind("tcp://{}:{}".format(ip, port))

    def send(self, topic, data):
        self.zmq_socket.send_string("{} {}".format(topic, data))

    def close(self):
        self.zmq_socket.close()
        self.zmq_context.term()


class ZeroMQSubscriber:
    def __init__(self, ip, port, topic):
        self.ip = ip
        self.port = port
        self.topic = topic
        self.zmq_context = zmq.Context()
        self.zmq_socket = self.zmq_context.socket(zmq.SUB)
        self.zmq_socket.setsockopt_string(zmq.SUBSCRIBE, topic)
        self.zmq_socket.connect("tcp://{}:{}".format(ip, port))

    def recv(self):
        # 返回的数据格式为：topic data
        feedback = self.zmq_socket.recv_string()
        # 将数据分割为topic和data
        topic, data = feedback.split(" ", 1)

        return topic, data

    def close(self):
        self.zmq_socket.close()
        self.zmq_context.term()


if __name__ == "__main__":
    # 创建一个ZeroMQPublisher实例
    publisher = ZeroMQPublisher("127.0.0.1", 5555)

    # 創建一個ZeroMQSubscriber實例
    subscriber = ZeroMQSubscriber("127.0.0.1", 5555, "account")


    # 現在用一個線程來發送消息
    def send_message():
        while True:
            publisher.send("account", "balance")
            time.sleep(1)


    # 現在用一個線程來接收消息
    def recv_message():
        while True:
            topic, data = subscriber.recv()
            print(topic, data)


    import threading

    t1 = threading.Thread(target=send_message)
    t2 = threading.Thread(target=recv_message)

    t1.start()
    t2.start()

    t1.join()
    t2.join()

    publisher.close()
    subscriber.close()

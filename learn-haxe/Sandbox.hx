class Sandbox {
  static public function main() {
    var hello: Array<String> = ["H","e","l","l","o"," ","W","o","r","l","d","!"];
    var numbers: Array<Int> = [for (i in 0...10) i];
    trace(hello.join(""));
    trace(numbers);
  }
}
public interface Employee {
  public void arriveAtWork();
  public void leaveWork();
  public void begnTimeBox(String type);
  public void endTimeBox();
  public void askQuestion();
  public void answerQuestion();
  public void doWork(int nextTimebox);
  public boolean inTimebox();
  public boolean inTheBuilding();
}

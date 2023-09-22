package model;

public class InHouse extends Part{
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId the machine Id to set if part is InHouse
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     *
     * @return machine Id
     */
    public int getMachineId(){
        return machineId;
    }
}

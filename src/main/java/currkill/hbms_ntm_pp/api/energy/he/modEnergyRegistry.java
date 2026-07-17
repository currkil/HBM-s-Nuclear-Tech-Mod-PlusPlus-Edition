package currkill.hbms_ntm_pp.api.energy.he;


//机器能量方法
public class modEnergyRegistry {
    private long energy=0;
    private final long maxPower;

    public modEnergyRegistry(long maxPower) {
        this.maxPower = maxPower;
    }
    //注册

    public long getPower() {
        return energy;
    }
    //获取能量

    public void setPower(long power) {
        this.energy = Math.max(0, Math.min(power, maxPower));
    }
    //设置能量

    public long getMaxPower() {
        return maxPower;
    }
    //得到最大能量

    public long addPower(long amount) {
        long space = maxPower - energy;
        long added = Math.min(space, amount);
        energy += added;
        return added;
    }
    public long usePower(long amount) {
        long used = Math.min(energy, amount);
        energy -= used;
        return used;
    }
    //返回实际加减，加减能量

    public boolean isFull() {
        return energy >= maxPower;
    }
    public boolean isEmpty() {
        return energy <= 0;
    }
    public double getFillRatio() {
        return (double) energy / (double) maxPower;
    }//逻辑判断类
}

package nssa.ad.detect;

import java.util.Map;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class DetectCore {

	public static String name() {
		Map<String, String> map = System.getenv();
		String computerName = map.get("COMPUTERNAME");
		return computerName;
	}

	public static String domain() {
		Map<String, String> map = System.getenv();
		String userDomain = map.get("USERDOMAIN");
		return userDomain;
	}

	public static String memory() {
		Sigar sigar = new Sigar();
		Mem mem;
		String memory = null;
		try {
			mem = sigar.getMem();
			memory = mem.getTotal() / 1024L / 1024L + "MB";
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return memory;
	}

	public static String cpu() {
		Sigar sigar = new Sigar();
		CpuInfo infos[];
		String cpu = null;
		try {
			infos = sigar.getCpuInfoList();
			CpuInfo info = infos[0];
			cpu = info.getVendor() + " " + info.getModel();
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return cpu;
	}

	public static String os() {
		OperatingSystem OS = OperatingSystem.getInstance();
		return OS.getDescription() + " (" + OS.getDataModel() + ")";
	}

	public static String disk() {
		Sigar sigar = new Sigar();
		FileSystem fslist[];
		String total = null;
		try {
			fslist = sigar.getFileSystemList();
			long fileTotal = 0;
			for (int i = 0; i < fslist.length; i++) {
				FileSystem fs = fslist[i];
				switch (fs.getType()) {
				case 0: // TYPE_UNKNOWN £ºÎ´Öª
					break;
				case 1: // TYPE_NONE
					break;
				case 2: // TYPE_LOCAL_DISK : ±¾µØÓ²ÅÌ
					FileSystemUsage usage = null;
					usage = sigar.getFileSystemUsage(fs.getDirName());
					fileTotal += usage.getTotal();
					break;
				case 3:// TYPE_NETWORK £ºÍøÂç
					break;
				case 4:// TYPE_RAM_DISK £ºÉÁ´æ
					break;
				case 5:// TYPE_CDROM £º¹âÇý
					break;
				case 6:// TYPE_SWAP £ºÒ³Ãæ½»»»
					break;
				}
			}
			total = fileTotal / 1024L + "MB";
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return total;
	}

	public static void main(String[] args) {
		try {
			System.out.println(name());
			System.out.println(domain());
			System.out.println(os());
			System.out.println(cpu());
			System.out.println(memory());
			System.out.println(disk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}

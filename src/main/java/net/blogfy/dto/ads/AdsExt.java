package net.blogfy.dto.ads;

import lombok.Getter;
import lombok.Setter;
import net.blogfy.entity.AdsInfo;

public class AdsExt extends AdsInfo {
	
	@Getter
	@Setter
	private double orderNum;
	
}

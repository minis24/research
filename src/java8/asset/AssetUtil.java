package java8.asset;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import java8.asset.Asset.AssetType;

public class AssetUtil {

	
	/**
	 * 예시(1) : 이터레이션 처리, 합계 되는 값, 합계를 구하는 방법등에 대한 로직이 뒤엉켜 있어서 재사용성 떨어짐.
	 * @param assets
	 * @return
	 */
	public static int totalAssetValues (final List<Asset> assets) {
		return assets.stream()
				.mapToInt(Asset::getValue)
				.sum();
	}
	
	
	/**
	 * 예시(2) : BOND 합계 처리 -->STOCK 합계와 코드중복 복붙..좀더 분리해 보자.
	 * @param assets
	 * @return
	 */
	public static int totalBondValues (final List<Asset> assets) {
		return assets.stream()
				.mapToInt(asset -> asset.getType() == Asset.AssetType.BOND ? asset.getValue() : 0 )
				.sum();
	}
	
	/**
	 * 예시(3) : STOCK 합계 처리 -->코드중복상황임.
	 * @param assets
	 * @return
	 */
	public static int totalStockValues (final List<Asset> assets) {
		return assets.stream()
				.mapToInt(asset -> asset.getType() == Asset.AssetType.STOCK ? asset.getValue() : 0 )
				.sum();
	}
	
	/**
	 * 예시(4) : filter() 메서드를 사용하여, assets리스트를 필터링.
	 * @param assets
	 * @return
	 */
	public static int totalAssetValues (final List<Asset> assets,final Predicate<Asset> assetSelector) {
		return assets.stream()
				.filter(assetSelector)
				.mapToInt(Asset::getValue )
				.sum();
	}
	
	
	public static void main(String[] args) {
		List<Asset> assets = 
					Arrays.asList(new Asset(Asset.AssetType.BOND,1000)
								 ,new Asset(Asset.AssetType.BOND,2000)
								 ,new Asset(Asset.AssetType.STOCK,3000)
								 ,new Asset(Asset.AssetType.STOCK,4000)
					);
		
		System.out.println("예시(1): Total of all Assets : "+ totalAssetValues(assets));
		System.out.println("예시(2): Total of bonds : "+ totalBondValues(assets));
		System.out.println("예시(3): Total of stocks : "+ totalStockValues(assets));
		
		// 모든 Asset 의 합계 구하기 : 모든 Asset의 합계를 계산하기 때문에 true를 리턴한다.
		System.out.println("예시(4): Total of all Assets(refactoring) : "+ totalAssetValues(assets,asset -> true));
		
		// BOND의 합계 구하기 : 
		System.out.println("예시(4): Total of BOND(refactoring) : "+ totalAssetValues(assets,asset -> asset.getType() == AssetType.BOND));
		
		// STOCK의 합계 구하기 : 
		System.out.println("예시(4): Total of STOCK(refactoring) : "+ totalAssetValues(assets,asset -> asset.getType() == AssetType.STOCK));
	}
	
}

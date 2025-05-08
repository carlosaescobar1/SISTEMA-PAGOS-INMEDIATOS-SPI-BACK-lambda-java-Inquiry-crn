package co.com.avc.util;


import co.com.avc.model.parameter.ParamActiveVault;
import co.com.avc.model.parameter.ParamVaultUpload;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class VaultSelectorUtil {

    private final ParamActiveVault paramActiveVault;

    public ParamVaultUpload recVaultSelector() {

        return paramActiveVault.getVaultsUpload().stream()
                .filter(vaultUpload -> vaultUpload.getVaultName()
                        .contains(paramActiveVault.getVaultRec().getVaultNameRec()))
                .findFirst()
                .orElse(null);

    }

    public List<ParamVaultUpload> syncVaultSelector(List<String> namesToExclude) {

        if (namesToExclude == null || namesToExclude.isEmpty()) {
            return new ArrayList<>(paramActiveVault.getVaultsUpload());
        }

        return paramActiveVault.getVaultsUpload().stream()
                .filter(vaultUpload -> !namesToExclude.contains(vaultUpload.getVaultName()))
                .collect(Collectors.toList());
    }

    public ParamVaultUpload vaultSelector(String vault) {

        return paramActiveVault.getVaultsUpload().stream()
                .filter(vaultUpload -> vaultUpload.getVaultName()
                        .equals(vault))
                .findFirst()
                .orElse(null);

    }

}





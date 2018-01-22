import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Veiculo e2e test', () => {

    let navBarPage: NavBarPage;
    let veiculoDialogPage: VeiculoDialogPage;
    let veiculoComponentsPage: VeiculoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Veiculos', () => {
        navBarPage.goToEntity('veiculo');
        veiculoComponentsPage = new VeiculoComponentsPage();
        expect(veiculoComponentsPage.getTitle())
            .toMatch(/controleEstacionamentoApp.veiculo.home.title/);

    });

    it('should load create Veiculo dialog', () => {
        veiculoComponentsPage.clickOnCreateButton();
        veiculoDialogPage = new VeiculoDialogPage();
        expect(veiculoDialogPage.getModalTitle())
            .toMatch(/controleEstacionamentoApp.veiculo.home.createOrEditLabel/);
        veiculoDialogPage.close();
    });

   /* it('should create and save Veiculos', () => {
        veiculoComponentsPage.clickOnCreateButton();
        veiculoDialogPage.setPlacaInput('placa');
        expect(veiculoDialogPage.getPlacaInput()).toMatch('placa');
        veiculoDialogPage.setModeloInput('modelo');
        expect(veiculoDialogPage.getModeloInput()).toMatch('modelo');
        veiculoDialogPage.setCorInput('cor');
        expect(veiculoDialogPage.getCorInput()).toMatch('cor');
        veiculoDialogPage.clienteSelectLastOption();
        veiculoDialogPage.save();
        expect(veiculoDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class VeiculoComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-veiculo div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class VeiculoDialogPage {
    modalTitle = element(by.css('h4#myVeiculoLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    placaInput = element(by.css('input#field_placa'));
    modeloInput = element(by.css('input#field_modelo'));
    corInput = element(by.css('input#field_cor'));
    clienteSelect = element(by.css('select#field_cliente'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setPlacaInput = function(placa) {
        this.placaInput.sendKeys(placa);
    }

    getPlacaInput = function() {
        return this.placaInput.getAttribute('value');
    }

    setModeloInput = function(modelo) {
        this.modeloInput.sendKeys(modelo);
    }

    getModeloInput = function() {
        return this.modeloInput.getAttribute('value');
    }

    setCorInput = function(cor) {
        this.corInput.sendKeys(cor);
    }

    getCorInput = function() {
        return this.corInput.getAttribute('value');
    }

    clienteSelectLastOption = function() {
        this.clienteSelect.all(by.tagName('option')).last().click();
    }

    clienteSelectOption = function(option) {
        this.clienteSelect.sendKeys(option);
    }

    getClienteSelect = function() {
        return this.clienteSelect;
    }

    getClienteSelectedOption = function() {
        return this.clienteSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
